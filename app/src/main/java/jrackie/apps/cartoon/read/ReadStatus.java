package jrackie.apps.cartoon.read;

/**
 * Created by Administrator on 2016/3/11.
 */
public class ReadStatus {
    private int episodeLast;
    private boolean isHorizontalTP;
    private boolean isLandscapeMode;
    private boolean isLeftHandTP;
    private boolean lightFS;
    private int lightProgress;
    private boolean nightMode;
    private int page;

    public ReadStatus(int episodeLast, boolean isHorizontalTP, boolean isLandscapeMode, boolean isLeftHandTP, boolean lightFS, int lightProgress, boolean nightMode, int page) {
        this.episodeLast = episodeLast;
        this.isHorizontalTP = isHorizontalTP;
        this.isLandscapeMode = isLandscapeMode;
        this.isLeftHandTP = isLeftHandTP;
        this.lightFS = lightFS;
        this.lightProgress = lightProgress;
        this.nightMode = nightMode;
        this.page = page;
    }

    public int getEpisodeLast() {
        return episodeLast;
    }

    public void setEpisodeLast(int episodeLast) {
        this.episodeLast = episodeLast;
    }

    public boolean isHorizontalTP() {
        return isHorizontalTP;
    }

    public void setIsHorizontalTP(boolean isHorizontalTP) {
        this.isHorizontalTP = isHorizontalTP;
    }

    public boolean isLandscapeMode() {
        return isLandscapeMode;
    }

    public void setIsLandscapeMode(boolean isLandscapeMode) {
        this.isLandscapeMode = isLandscapeMode;
    }

    public boolean isLeftHandTP() {
        return isLeftHandTP;
    }

    public void setIsLeftHandTP(boolean isLeftHandTP) {
        this.isLeftHandTP = isLeftHandTP;
    }

    public boolean isLightFS() {
        return lightFS;
    }

    public void setLightFS(boolean lightFS) {
        this.lightFS = lightFS;
    }

    public int getLightProgress() {
        return lightProgress;
    }

    public void setLightProgress(int lightProgress) {
        this.lightProgress = lightProgress;
    }

    public boolean isNightMode() {
        return nightMode;
    }

    public void setNightMode(boolean nightMode) {
        this.nightMode = nightMode;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }


}
