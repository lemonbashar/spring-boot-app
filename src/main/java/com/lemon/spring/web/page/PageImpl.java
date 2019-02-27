package com.lemon.spring.web.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SuppressWarnings("unused")
public class PageImpl implements Pageable {
    private int page;
    private int size;
    private Sort sort;

    public PageImpl(int size) {
        this(0,size);
    }

    public PageImpl(int page, int size) {
        this(page,size,Sort.unsorted());
    }

    public PageImpl(int page, int size,String sortBy) {
        this(page,size,Sort.by(sortBy));
    }



    public PageImpl(int page, int size, Sort sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    /**
     * Returns the page to be returned.
     *
     * @return the page to be returned.
     */
    @Override
    public int getPageNumber() {
        return page;
    }

    /**
     * Returns the number of items to be returned.
     *
     * @return the number of items of that page
     */
    @Override
    public int getPageSize() {
        return size;
    }

    /**
     * Returns the offset to be taken according to the underlying page and page size.
     *
     * @return the offset to be taken
     */
    @Override
    public long getOffset() {
        return (long)page*(long)size;
    }

    /**
     * Returns the sorting parameters.
     *
     * @return sort
     */
    @Override
    public Sort getSort() {
        return sort;
    }

    /**
     * Returns the {@link Pageable} requesting the next {@link Page}.
     *
     * @return next page
     */
    @Override
    public Pageable next() {
        return new PageImpl(getPageNumber()+1,getPageSize(),getSort());
    }

    /**
     * Returns the previous {@link Pageable} or the first {@link Pageable} if the current one already is the first one.
     *
     * @return previous page
     */
    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    private Pageable previous() {
        return getPageNumber() == 0 ? this : new PageImpl(getPageNumber() - 1, getPageSize(), getSort());
    }

    /**
     * Returns the {@link Pageable} requesting the first page.
     *
     * @return first page
     */
    @Override
    public Pageable first() {
        return new PageImpl(0,getPageSize(),getSort());
    }

    /**
     * Returns whether there's a previous {@link Pageable} we can access from the current one. Will return
     * {@literal false} in case the current {@link Pageable} already refers to the first page.
     *
     * @return true if it has previous page
     */
    @Override
    public boolean hasPrevious() {
        return page > 0;
    }
}
