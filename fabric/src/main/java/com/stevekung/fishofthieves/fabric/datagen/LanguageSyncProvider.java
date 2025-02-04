package com.stevekung.fishofthieves.fabric.datagen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
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
            var mainTranslationJson = basePath.resolve("common/src/main/resources/assets/fishofthieves/lang/en_us.json"); // en_us.json file
            var otherTranslations = basePath.resolve("common/src/main/resources/assets/fishofthieves/lang/"); // Scan for other translations
            var mapper = new ObjectMapper();

            try
            {
                // Load the en_us.json while preserving key order
                var mainJson = mapper.readTree(mainTranslationJson.toFile());
                var orderedMainJson = Maps.<String, String>newLinkedHashMap();
                mainJson.fields().forEachRemaining(entry -> orderedMainJson.put(entry.getKey(), entry.getValue().asText()));

                // Iterate through all JSON files in the directory
                Files.list(otherTranslations).filter(path -> path.toString().endsWith(".json") && !path.toString().equals("en_us.json")).forEach(path ->
                {
                    if (!path.equals(mainTranslationJson))
                    {
                        try
                        {
                            // Load the current translation file while preserving key order
                            var translationJson = mapper.readTree(path.toFile());
                            var orderedTranslationJson = Maps.<String, String>newLinkedHashMap();
                            translationJson.fields().forEachRemaining(entry -> orderedTranslationJson.put(entry.getKey(), entry.getValue().asText()));

                            // Sync keys with main JSON file
                            var modified = false;
                            var updatedJson = new StringBuilder();
                            updatedJson.append("{");

                            for (var entry : orderedMainJson.entrySet())
                            {
                                var key = entry.getKey();

                                if (orderedTranslationJson.containsKey(key))
                                {
                                    updatedJson.append(String.format("\n  \"%s\": \"%s\",", key, orderedTranslationJson.get(key)));
                                }
                                else
                                {
                                    updatedJson.append(String.format("\n  \"%s\": \"%s\",", key, entry.getValue()));
                                    modified = true;
                                }
                            }

                            if (updatedJson.charAt(updatedJson.length() - 1) == ',')
                            {
                                updatedJson.setCharAt(updatedJson.length() - 1, '\n');
                            }
                            updatedJson.append("}");

                            // Save changes if any modifications were made
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