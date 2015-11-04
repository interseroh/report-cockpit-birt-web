package de.interseroh.report.pagination;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class PageLink {

    private long pageNumber;

    private boolean active;

    private PageLinkType linkType;

    public PageLink(long pageNumber, boolean active, PageLinkType linkType) {
        this.pageNumber = pageNumber;
        this.active = active;
        this.linkType = linkType;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public boolean isActive() {
        return active;
    }

    public PageLinkType getLinkType() {
        return linkType;
    }

    @Override
    public String toString() {
        return "\nPageLink{" +
                "pageNumber=" + pageNumber +
                ", active=" + active +
                ", linkType=" + linkType +
                '}';
    }
}
