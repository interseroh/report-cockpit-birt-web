package de.interseroh.report.pagination;

import java.util.List;

import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class PaginationTest {

	@Test
	public void testConstructor() throws Exception {
		Pagination pagination = new Pagination(1, 5);
		assertThat(pagination.getCurrentPageNumber(), is(1L));
		assertThat(pagination.getNumberOfPages(), is(5l));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testConstructorError() throws Exception {
		new Pagination(5, 1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testConstructorErrorMessage() throws Exception {
		try {
			new Pagination(5, 1);
		} catch (IndexOutOfBoundsException e) {
			assertThat(e.getMessage(), containsString("1 and 1. It is 5"));
			throw e;
		}
	}

	@Test
	public void testOnlyOnePage() throws Exception {
        assertThat(new Pagination(1,1).getPageLinks().isEmpty(), is(true));
    }

    @Test
	public void testPageLinksWithThreeNine() throws Exception {
		List<PageLink> links = new Pagination(3, 9, 3, false).getPageLinks();
		assertThat(links.size(), is(5));
		assertThat(links.get(0).getLinkType(), is(PageLinkType.FIRST));
		assertThat(links.get(0).getPageNumber(), is(1L));
		assertThat(links.get(0).isActive(), is(false));
		assertThat(links.get(1).getLinkType(), is(PageLinkType.PAGE));
		assertThat(links.get(1).getPageNumber(), is(2L));
		assertThat(links.get(1).isActive(), is(false));
		assertThat(links.get(2).getLinkType(), is(PageLinkType.PAGE));
		assertThat(links.get(2).getPageNumber(), is(3L));
		assertThat(links.get(2).isActive(), is(true));
		assertThat(links.get(3).getLinkType(), is(PageLinkType.PAGE));
		assertThat(links.get(3).getPageNumber(), is(4L));
		assertThat(links.get(3).isActive(), is(false));
		assertThat(links.get(4).getLinkType(), is(PageLinkType.LAST));
		assertThat(links.get(4).getPageNumber(), is(9L));
		assertThat(links.get(4).isActive(), is(false));
	}

    @Test
    public void testPageLinksWithThreeNineFirstActive() throws Exception {
        Pagination pagination = new Pagination(1, 9, 3, false);
        System.out.println(pagination);
        List<PageLink> links = pagination.getPageLinks();
        assertThat(links.size(), is(5));
        assertThat(links.get(0).getLinkType(), is(PageLinkType.FIRST));
        assertThat(links.get(0).getPageNumber(), is(1L));
        assertThat(links.get(0).isActive(), is(true));
        assertThat(links.get(1).getLinkType(), is(PageLinkType.PAGE));
        assertThat(links.get(1).getPageNumber(), is(1L));
        assertThat(links.get(1).isActive(), is(true));
        assertThat(links.get(2).getLinkType(), is(PageLinkType.PAGE));
        assertThat(links.get(2).getPageNumber(), is(2L));
        assertThat(links.get(2).isActive(), is(false));
        assertThat(links.get(3).getLinkType(), is(PageLinkType.PAGE));
        assertThat(links.get(3).getPageNumber(), is(3L));
        assertThat(links.get(3).isActive(), is(false));
        assertThat(links.get(4).getLinkType(), is(PageLinkType.LAST));
        assertThat(links.get(4).getPageNumber(), is(9L));
        assertThat(links.get(4).isActive(), is(false));
    }

    @Test
    public void testPageLinksWithThreeNineLastActive() throws Exception {
        Pagination pagination = new Pagination(9, 9, 5, false);
        System.out.println(pagination);
        List<PageLink> links = pagination.getPageLinks();
        assertThat(links.size(), is(7));
        assertThat(links.get(0).getLinkType(), is(PageLinkType.FIRST));
        assertThat(links.get(0).getPageNumber(), is(1L));
        assertThat(links.get(0).isActive(), is(false));
        assertThat(links.get(1).getLinkType(), is(PageLinkType.PAGE));
        assertThat(links.get(1).getPageNumber(), is(5L));
        assertThat(links.get(1).isActive(), is(false));
        assertThat(links.get(2).getLinkType(), is(PageLinkType.PAGE));
        assertThat(links.get(2).getPageNumber(), is(6L));
        assertThat(links.get(2).isActive(), is(false));
        assertThat(links.get(3).getLinkType(), is(PageLinkType.PAGE));
        assertThat(links.get(3).getPageNumber(), is(7L));
        assertThat(links.get(3).isActive(), is(false));
        assertThat(links.get(4).getLinkType(), is(PageLinkType.PAGE));
        assertThat(links.get(4).getPageNumber(), is(8L));
        assertThat(links.get(4).isActive(), is(false));
        assertThat(links.get(5).getLinkType(), is(PageLinkType.PAGE));
        assertThat(links.get(5).getPageNumber(), is(9L));
        assertThat(links.get(5).isActive(), is(true));
        assertThat(links.get(6).getLinkType(), is(PageLinkType.LAST));
        assertThat(links.get(6).getPageNumber(), is(9L));
        assertThat(links.get(6).isActive(), is(true));
    }


    @Test
    public void testPageLinksWithFastForwardActive() throws Exception {
        Pagination pagination = new Pagination(5, 20, 5, true);
        System.out.println(pagination);
        List<PageLink> links = pagination.getPageLinks();
        assertThat(links.size(), is(9));
        assertThat(links.get(0).getLinkType(), is(PageLinkType.FIRST));
        assertThat(links.get(0).getPageNumber(), is(1L));
        assertThat(links.get(0).isActive(), is(false));
        assertThat(links.get(1).getLinkType(), is(PageLinkType.FASTBACKWARD));
        assertThat(links.get(1).getPageNumber(), is(1L));
        assertThat(links.get(1).isActive(), is(false));
        assertThat(links.get(7).getLinkType(), is(PageLinkType.FASTFORWARD));
        assertThat(links.get(7).getPageNumber(), is(12L));
        assertThat(links.get(7).isActive(), is(false));
        assertThat(links.get(8).getLinkType(), is(PageLinkType.LAST));
        assertThat(links.get(8).getPageNumber(), is(20L));
        assertThat(links.get(8).isActive(), is(false));
    }

    @Test
	public void testPageLinksWithThreePages() throws Exception {
		List<PageLink> links = new Pagination(2, 3, 5 , false).getPageLinks();
		assertThat(links.size(), is(3));
		assertThat(links.get(0).getLinkType(), is(PageLinkType.PAGE));
		assertThat(links.get(0).getPageNumber(), is(1L));
		assertThat(links.get(0).isActive(), is(false));
		assertThat(links.get(1).getLinkType(), is(PageLinkType.PAGE));
		assertThat(links.get(1).getPageNumber(), is(2L));
		assertThat(links.get(1).isActive(), is(true));
		assertThat(links.get(2).getLinkType(), is(PageLinkType.PAGE));
		assertThat(links.get(2).getPageNumber(), is(3L));
		assertThat(links.get(2).isActive(), is(false));
	}

	@Test
	public void testPageLinksWithTwo() throws Exception {
		List<PageLink> links = new Pagination(2, 2).getPageLinks();
		assertThat(links.size(), is(2));
		assertThat(links.get(0).getLinkType(), is(PageLinkType.PAGE));
		assertThat(links.get(0).isActive(), is(false));
		assertThat(links.get(1).getLinkType(), is(PageLinkType.PAGE));
		assertThat(links.get(1).isActive(), is(true));
	}





}