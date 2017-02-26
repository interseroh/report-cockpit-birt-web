package de.interseroh.report.pagination;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class Pagination {


    private long maxNumberOfLinks = 5;
    private long currentPage;
    private boolean withFastForward;
    private long numberOfPages;
    private List<PageLink> pageLinks;

    public Pagination(final long currentPage, final long numberOfPages, final long maxNumberOfLinks, final boolean withFastForward)
            throws IndexOutOfBoundsException {
        if (currentPage < 1 || currentPage > numberOfPages)
            throw new IndexOutOfBoundsException(String.format(
                    "CurrentPage is out of range. It must be between 1 and {}. It is {}.",
                    numberOfPages, currentPage));
        if (maxNumberOfLinks < 2)
            throw new IllegalArgumentException("MaxNumberOfLinks must be greater than 1");
        this.maxNumberOfLinks = maxNumberOfLinks;
        this.currentPage = currentPage;
        this.numberOfPages = numberOfPages;
        this.withFastForward = withFastForward;
        initPageLinks();
    }

    public Pagination(final long currentPage, final long numberOfPages) {
        this(currentPage, numberOfPages, 5, true);
    }


    private void initPageLinks() {
        pageLinks = new LinkedList<>();

        if (isFirstPageLinkNeeded()) {
            pageLinks.add(new PageLink(1, isFirstPage(), PageLinkType.FIRST));
        }

        if (isFastForwardedPossible()) {
            pageLinks.add(new PageLink(fastBackwardPage(), false, PageLinkType.FASTBACKWARD));
        }

        if (numberOfPages > 1) {
            for (long pageNumber = numberOfBeginLink(); pageNumber <= numberOfEndLink(); pageNumber++) {
                pageLinks.add(new PageLink(pageNumber, isCurrentPage(pageNumber), PageLinkType.PAGE));
            }
        }

        if (isFastForwardedPossible()) {
            pageLinks.add(new PageLink(fastForwardPage(), false, PageLinkType.FASTFORWARD));
        }

        if (isLastPageLinkNeeded()) {
            pageLinks.add(new PageLink(numberOfPages, isLastPage(), PageLinkType.LAST));
        }
    }

    private long numberOfBeginLink() {
        long offset = getMaxNumbersOfLinks() / 2;
        long diff = numberOfPages - offset - currentPage;
        diff = diff < 0 ? -diff : 0;
        long linksBegin = currentPage - offset - diff;
        return (linksBegin < 1) ? 1 : linksBegin;
    }

    private long numberOfEndLink() {
        long offset = getMaxNumbersOfLinks() / 2;
        long diff = ((currentPage - offset) < 1 )? -(currentPage - offset)+1 : 0;
        long linksEnd = currentPage + offset + diff;

        return (linksEnd > numberOfPages) ? numberOfPages : linksEnd;

    }

    public boolean isFirstPageLinkNeeded() {
        return maxNumberOfLinks < numberOfPages;
    }

    public boolean isLastPageLinkNeeded() {
        return maxNumberOfLinks < numberOfPages;
    }

    private long getMaxNumbersOfLinks() {
        return Math.min(numberOfPages, maxNumberOfLinks);
    }

    public boolean isLastPage() {
        return currentPage == numberOfPages;
    }

    public boolean isFirstPage() {
        return currentPage == 1L;
    }

    public boolean isCurrentPage(long pageNumber) {
        return pageNumber == currentPage;
    }

    public long getCurrentPageNumber() {
        return currentPage;
    }

    public long getNumberOfPages() {
        return numberOfPages;
    }

    public List<PageLink> getPageLinks() {
        return Collections.unmodifiableList(pageLinks);
    }

    public boolean isFastForwardedPossible() {
        return maxNumberOfLinks + 10 < numberOfPages && withFastForward;
    }

    public long fastBackwardPage() {
        long fastBackwardPage = numberOfBeginLink() - 5;
        return (fastBackwardPage < 1) ? 1 : fastBackwardPage;
    }

    public long fastForwardPage() {
        long fastForwardPage = numberOfEndLink() + 5;
        return (fastForwardPage < numberOfPages) ? fastForwardPage : numberOfPages;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "maxNumberOfLinks=" + maxNumberOfLinks +
                ", currentPage=" + currentPage +
                ", numberOfPages=" + numberOfPages +
                ", pageLinks=" + pageLinks +
                '}';
    }

}
