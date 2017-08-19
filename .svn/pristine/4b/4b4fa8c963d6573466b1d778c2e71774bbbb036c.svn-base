package com.vsoftcorp.kls.dataaccess.pagination;

import java.util.List;

import javax.persistence.EntityManager;

public class ResultPagerBean {

	private String queryName;
	private long currentPage;
	private long maxResults;
	private long pageSize;

	public void init(long pageSize, String countQueryName, String queryName,
			EntityManager em,List<Integer> pacs) {
		this.pageSize = pageSize;
		this.queryName = queryName;
		maxResults = em.createNamedQuery(countQueryName, Long.class).setParameter("pacs", pacs)
				.getSingleResult();
		currentPage = 0;

	}

	public List getCurrentResults(EntityManager em,List<Integer> pacs) {
		return em.createNamedQuery(queryName).setParameter("pacs", pacs)
				.setFirstResult((int) (currentPage * pageSize))
				.setMaxResults((int) pageSize).getResultList();
	}

	public void next() {
		currentPage++;
	}

	public void previous() {
		currentPage--;
		if (currentPage < 0) {
			currentPage = 0;
		}
	}

	public long getMaxPages() {
		return maxResults / pageSize;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public void finished() {
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public long getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(long maxResults) {
		this.maxResults = maxResults;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

}