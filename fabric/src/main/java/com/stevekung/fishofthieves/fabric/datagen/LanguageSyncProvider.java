package com.stevekung.fishofthieves.fabric.datagen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

public class LanguageSyncProvider implements DataProvider
{
    private static final Pattern ENTRY_PATTERN = Pattern.compile("\"([^\"]*)\"\\s*:\\s*\"([^\"]*)\"\\s*,?");
    private static final String KEY_VALUE_FORMAT = "\n  \"%s\": \"%s\"";
    private final CompletableFuture<HolderLookup.Provider> provider;

    public LanguageSyncProvider(CompletableFuture<HolderLookup.Provider> provider)
    {
        this.provider = provider;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output)
    {
        return this.provider.thenCompose(ignored -> CompletableFuture.runAsync(() ->
        {
            var basePath = Paths.get("").toAbsolutePath().getParent().getParent().getParent();
            var mainTranslationJson = basePath.resolve("common/src/main/resources/assets/fishofthieves/lang/en_us.json");
            var otherTranslations = basePath.resolve("common/src/main/resources/assets/fishofthieves/lang/");

            try
            {
                var mainJson = Files.readString(mainTranslationJson);
                var lines = mainJson.split("\\R");
                var orderedMainJson = new LinkedHashMap<String, String>();
                var lineBreaks = new LinkedHashMap<String, Boolean>();

                for (var i = 0; i < lines.length; i++)
                {
                    var line = lines[i].trim();

                    if (line.isEmpty())
                    {
                        continue;
                    }
                    if (line.endsWith(",") && i < lines.length - 1)
                    {
                        line = line.substring(0, line.length() - 1);
                    }

                    var matcher = ENTRY_PATTERN.matcher(line);

                    if (matcher.find())
                    {
                        var key = matcher.group(1);
                        var value = matcher.group(2);

                        orderedMainJson.put(key, value);
                        lineBreaks.put(key, i > 0 && lines[i - 1].trim().isEmpty());
                    }
                }

                try (var files = Files.list(otherTranslations))
                {
                    files.filter(path -> path.toString().endsWith(".json") && !path.toString().equals("en_us.json")).forEach(path ->
                    {
                        try
                        {
                            var translationJson = Files.readString(path);
                            var translationLines = translationJson.split("\\R");
                            var orderedTranslationJson = new LinkedHashMap<String, String>();

                            for (var line : translationLines)
                            {
                                if (line.trim().isEmpty() || !line.contains(":"))
                                {
                                    continue;
                                }

                                var matcher = ENTRY_PATTERN.matcher(line);

                                if (matcher.find())
                                {
                                    var key = matcher.group(1);
                                    var value = matcher.group(2);
                                    orderedTranslationJson.put(key, value);
                                }
                            }

                            var modified = false;
                            var updatedJson = new StringBuilder();
                            updatedJson.append("{");

                            var entrySet = orderedMainJson.entrySet();
                            var iterator = entrySet.iterator();

                            while (iterator.hasNext())
                            {
                                var entry = iterator.next();
                                var key = entry.getKey();
                                var value = entry.getValue();

                                if (lineBreaks.get(key))
                                {
                                    updatedJson.append("\n");
                                }

                                if (orderedTranslationJson.containsKey(key))
                                {
                                    updatedJson.append(String.format(KEY_VALUE_FORMAT, key, orderedTranslationJson.get(key)));
                                }
                                else
                                {
                                    updatedJson.append(String.format(KEY_VALUE_FORMAT, key, value));
                                    modified = true;
                                }

                                if (iterator.hasNext())
                                {
                                    updatedJson.append(",");
                                }
                            }

                            updatedJson.append("\n}");

                            if (modified)
                            {
                                Files.writeString(path, updatedJson.toString());
                                FishOfThieves.LOGGER.info("Updated: {}", path.getFileName());
                            }
                        }
                        catch (IOException e)
                        {
                            FishOfThieves.LOGGER.error("Failed to process: {}", path.getFileName(), e);
                        }
                    });
                }
            }
            catch (IOException e)
            {
                FishOfThieves.LOGGER.error("Error loading main JSON file", e);
            }
        }));
    }

    @Override
    public String getName()
    {
        return "Language Sync";
    }
}