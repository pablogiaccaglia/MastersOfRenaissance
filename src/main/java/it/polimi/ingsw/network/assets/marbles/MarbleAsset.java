package it.polimi.ingsw.network.assets.marbles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.network.assets.devcards.NetworkResource;
import it.polimi.ingsw.network.jsonUtils.JsonUtility;
import it.polimi.ingsw.server.utils.Deserializator;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 *  <p>Enum class for <em>Market Marbles</em> of MarketBoard<br>
 *  Each Marble but White is mapped to a different {@link NetworkResource Resource}.
 *  <ul>
 *  <li>{@link #WHITE}
 *  <li>{@link #BLUE}
 *  <li>{@link #GRAY}
 *  <li>{@link #YELLOW}
 *  <li>{@link #PURPLE}
 *  <li>{@link #RED}
 *  </ul>
 */
public enum MarbleAsset {
    /**
     * According to the official rulebook this <em>Marble</em> hasn't a mapped {@link NetworkResource Resource}. <br>
     * Any possible mapping to a resource due to dedicated leader effect is done in the MarketBoard
     */
    WHITE(Paths.get("src/main/resources/assets/marbles/Masters of Renaissance_Marbles_WHITE.png")),
    /**
     * This <em>Marble</em> is mapped to the {@link NetworkResource#SHIELD SHIELD} Resource
     */
    BLUE((Paths.get("src/main/resources/assets/marbles/Masters of Renaissance_Marbles_BLUE.png"))),

    /**
     * This <em>Marble</em> is mapped to the {@link NetworkResource#STONE STONE} Resource
     */
    GRAY((Paths.get("src/main/resources/assets/marbles/Masters_of_Renaissance_Marbles_GRAY.png"))),

    /**
     * This <em>Marble</em> is mapped to the {@link NetworkResource#GOLD GOLD} Resource
     */
    YELLOW((Paths.get("src/main/resources/assets/marbles/Masters of Renaissance_Marbles_YELLOW.png"))),

    /**
     * This <em>Marble</em> is mapped to the {@link NetworkResource#SERVANT SERVANT} Resource
     */
    PURPLE((Paths.get("src/main/resources/assets/marbles/Masters of Renaissance_Marbles_PURPLE.png"))),

    /**
     * This <em>Marble</em> is mapped to the {@link NetworkResource#FAITH FAITH} Resource
     */
    RED((Paths.get("src/main/resources/assets/marbles/Masters of Renaissance_Marbles_RED.png"))),

    INVALID((Paths.get("src/main/resources/config/dummypath")));


    private Path marbleAssetPath;
    private static final MarbleAsset[] vals = MarbleAsset.values();

    MarbleAsset(){}

    MarbleAsset(final Path marbleAssetPath) {
        this.marbleAssetPath = marbleAssetPath;
        }

    public Path getPath() {
        return this.marbleAssetPath;
    }

    public static MarbleAsset fromInt(int marbleNum){
        return marbleNum>vals.length|| marbleNum<0 ? INVALID: vals[marbleNum];
    }

    public String getName() {
        return this.name();
    }

    public static MarbleAsset fromKey(String enumName) {
        for(MarbleAsset marbleAsset : MarbleAsset.values()) {
            if (marbleAsset.getName().equals(enumName)) {
                return marbleAsset;
            }
        }
        return null;
    }

    private void setPath(Path path){
        this.marbleAssetPath = path;
    }

    public static void initializeMarblesFromConfig(String path){

        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().registerTypeHierarchyAdapter(Path.class, new JsonUtility.PathConverter()).registerTypeAdapter(Enum.class, new it.polimi.ingsw.client.json.Deserializator.MarblesDeserializer()).create();
        Type type = new TypeToken <Map<MarbleAsset, Path>>(){}.getType();
        Map<MarbleAsset, Path> marblesMap = Deserializator.deserialize(path, type, gson);

        marblesMap.forEach(MarbleAsset::setPath);

    }

}
