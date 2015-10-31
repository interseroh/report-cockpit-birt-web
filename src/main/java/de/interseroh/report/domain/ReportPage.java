package de.interseroh.report.domain;

/**
 * class to handle pagination. Set current and last page number.
 *
 * @author Ingo Düppe (Crowdcode)
 */
public class ReportPage {

    private long pageNumbers;

    private long currentPageNumber;

    public long getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(long pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    public long getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(long currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public long getNextPageNumber() {
        return currentPageNumber + 1;
    }

    public long getPreviousPageNumber() {
        if(currentPageNumber == 1) {
            return currentPageNumber;
        } else {
            return currentPageNumber - 1;
        }
    }

    public boolean isFirstPage() {
        return currentPageNumber == 1;
    }

    public boolean isLastPage() {
        return currentPageNumber == getPageNumbers();
    }

    public boolean isMoreThanOne() {
        return pageNumbers >1;
    }
}
