/**
 * 
 */
package save.our.planet;

/**
 * 
 */
public enum CharitySymbol {
	
	WAVE("🌊"),ROSE("🌷"),MOON("🌙"),EARTH("🌎") ;
	
	private final String emoji;

    // Constructor
	CharitySymbol(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }

}
