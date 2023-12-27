package com.botree.interdbentity.model;

/**
 * Key Generator Entity class.
 * @author vinodkumar.a
 */
public class KeyGeneratorEntity extends AbstractEntity {

    /** loginCode. */
    private String loginCode;

    /** screenName. */
    private String screenName;

    /** prefix. */
    private String prefix;

    /** suffixYY. */
    private String suffixYY;

    /** suffixNN. */
    private String suffixNN;

    /**
     * @return the loginCode
     */
    public final String getLoginCode() {
        return loginCode;
    }

    /**
     * @param loginCodeIn the loginCode to set
     */
    public final void setLoginCode(final String loginCodeIn) {
        loginCode = loginCodeIn;
    }

    /**
     * @return the screenName
     */
    public final String getScreenName() {
        return screenName;
    }

    /**
     * @param screenNameIn the screenName to set
     */
    public final void setScreenName(final String screenNameIn) {
        screenName = screenNameIn;
    }

    /**
     * @return the prefix
     */
    public final String getPrefix() {
        return prefix;
    }

    /**
     * @param prefixIn the prefix to set
     */
    public final void setPrefix(final String prefixIn) {
        prefix = prefixIn;
    }

    /**
     * @return the suffixYY
     */
    public final String getSuffixYY() {
        return suffixYY;
    }

    /**
     * @param suffixYYIn the suffixYY to set
     */
    public final void setSuffixYY(final String suffixYYIn) {
        suffixYY = suffixYYIn;
    }

    /**
     * @return the suffixNN
     */
    public final String getSuffixNN() {
        return suffixNN;
    }

    /**
     * @param suffixNNIn the suffixNN to set
     */
    public final void setSuffixNN(final String suffixNNIn) {
        suffixNN = suffixNNIn;
    }

}
