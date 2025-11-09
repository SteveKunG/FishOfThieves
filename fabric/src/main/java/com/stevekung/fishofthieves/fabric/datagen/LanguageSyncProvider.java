package com.stevekung.fishofthieves.fabric.datagen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

public class LanguageSyncProvider implements DataProvider
{
    private final CompletableFuture<HolderLookup.Provider> provider;

    public LanguageSyncProvider(CompletableFuture<HolderLookup.Provider> provider)
    {
        this.provider = provider;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output)
    {
        return this.provider.thenCompose(provider -> CompletableFuture.runAsync(() ->
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
                    if (line.contains(":"))
                    {
                        var parts = line.split(":", 2);
                        orderedMainJson.put(parts[0].trim().replace("\"", ""), parts[1].trim().replace("\"", ""));
                        lineBreaks.put(parts[0].trim().replace("\"", ""), i > 0 && lines[i - 1].trim().isEmpty());
                    }
                }

                Files.list(otherTranslations).filter(path -> path.toString().endsWith(".json") && !path.toString().equals("en_us.json")).forEach(path ->
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

                            var parts = line.trim().split(":", 2);

                            if (parts[1].trim().endsWith(","))
                            {
                                parts[1] = parts[1].trim().substring(0, parts[1].trim().length() - 1);
                            }
                            orderedTranslationJson.put(parts[0].trim().replace("\"", ""), parts[1].trim().replace("\"", ""));
                        }

                        var modified = false;
                        var updatedJson = new StringBuilder();
                        updatedJson.append("{");

                        var entrySet = orderedMainJson.entrySet();
                        var iterator = entrySet.iterator();

                        while (iterator.hasNext())
                        {
                            var entry = iterator.next();

                            if (lineBreaks.get(entry.getKey()))
                            {
                                updatedJson.append("\n");
                            }

                            if (orderedTranslationJson.containsKey(entry.getKey()))
                            {
                                updatedJson.append(String.format("\n  \"%s\": \"%s\"", entry.getKey(), orderedTranslationJson.get(entry.getKey())));
                            }
                            else
                            {
                                updatedJson.append(String.format("\n  \"%s\": \"%s\"", entry.getKey(), entry.getValue()));
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
                        FishOfThieves.LOGGER.error("Failed to process: {}", path.getFileName());
                    }
                });
            }
            catch (IOException e)
            {
                FishOfThieves.LOGGER.error("Error loading main JSON file: {}", e.getMessage());
            }
        }));
    }

    @Override
    public String getName()
    {
        return "Language Sync";
    }
}