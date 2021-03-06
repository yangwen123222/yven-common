package com.yven.mongodb.dao;

import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import com.yven.mongodb.config.MongoFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.DAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class TBaseDAO<T, K> implements DAO<T, K> {
	
	protected Class<T> entityClazz;
	protected DatastoreImpl ds = null;

	@SuppressWarnings("unchecked")
	public TBaseDAO(final Datastore ds) {
		this.ds = (DatastoreImpl) ds;
		initType(((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
	}

	@SuppressWarnings("unchecked")
	public TBaseDAO() {
		ParameterizedType pt =  ((ParameterizedType) getClass().getGenericSuperclass());
		Class<T> ct = (Class<T>)pt.getActualTypeArguments()[0];
		
		String className = ct.getName();
		ds = (DatastoreImpl) MongoFactory.getInstance(className).getDatastore(className);
		initType(ct);
	}

	protected void initType(final Class<T> type) {
		entityClazz = type;
		ds.getMapper().addMappedClass(type);
	}

	protected void initDS(final Mongo mon, final Morphia mor, final String db) {
		ds = new DatastoreImpl(mor, mon, db);
	}

	/**
	 * Converts from a List<Key> to their id values
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<?> keysToIds(final List<Key<T>> keys) {
		final List ids = new ArrayList(keys.size() * 2);
		for (final Key<T> key : keys) {
			ids.add(key.getId());
		}
		return ids;
	}

	/**
	 * The underlying collection for this DAO
	 */
	public DBCollection getCollection() {
		return ds.getCollection(entityClazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#createQuery()
	 */
	public Query<T> createQuery() {
		return ds.createQuery(entityClazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#createUpdateOperations()
	 */
	public UpdateOperations<T> createUpdateOperations() {
		return ds.createUpdateOperations(entityClazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#getEntityClass()
	 */
	public Class<T> getEntityClass() {
		return entityClazz;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#save(T)
	 */
	public Key<T> save(final T entity) {
		return ds.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#save(T, com.mongodb.WriteConcern)
	 */
	public Key<T> save(final T entity, final WriteConcern wc) {
		return ds.save(entity, wc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#updateFirst(org.mongodb.morphia.query.Query,
	 * org.mongodb.morphia.query.UpdateOperations)
	 */
	public UpdateResults<T> updateFirst(final Query<T> q,
			final UpdateOperations<T> ops) {
		return ds.updateFirst(q, ops);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#update(org.mongodb.morphia.query.Query,
	 * org.mongodb.morphia.query.UpdateOperations)
	 */
	public UpdateResults<T> update(final Query<T> q,
			final UpdateOperations<T> ops) {
		return ds.update(q, ops);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#delete(T)
	 */
	public WriteResult delete(final T entity) {
		return ds.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#delete(T, com.mongodb.WriteConcern)
	 */
	public WriteResult delete(final T entity, final WriteConcern wc) {
		return ds.delete(entity, wc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#deleteById(K)
	 */
	public WriteResult deleteById(final K id) {
		return ds.delete(entityClazz, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mongodb.morphia.DAO#deleteByQuery(org.mongodb.morphia.query.Query)
	 */
	public WriteResult deleteByQuery(final Query<T> q) {
		return ds.delete(q);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#get(K)
	 */
	public T get(final K id) {
		return ds.get(entityClazz, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#findIds(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List<K> findIds(final String key, final Object value) {
		return (List<K>) keysToIds(ds.find(entityClazz, key, value).asKeyList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#findIds()
	 */
	@SuppressWarnings("unchecked")
	public List<K> findIds() {
		return (List<K>) keysToIds(ds.find(entityClazz).asKeyList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#findIds(org.mongodb.morphia.query.Query)
	 */
	@SuppressWarnings("unchecked")
	public List<K> findIds(final Query<T> q) {
		return (List<K>) keysToIds(q.asKeyList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#exists(java.lang.String, java.lang.Object)
	 */
	public boolean exists(final String key, final Object value) {
		return exists(ds.find(entityClazz, key, value));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#exists(org.mongodb.morphia.query.Query)
	 */
	public boolean exists(final Query<T> q) {
		return ds.getCount(q) > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#count()
	 */
	public long count() {
		return ds.getCount(entityClazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#count(java.lang.String, java.lang.Object)
	 */
	public long count(final String key, final Object value) {
		return count(ds.find(entityClazz, key, value));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#count(org.mongodb.morphia.query.Query)
	 */
	public long count(final Query<T> q) {
		return ds.getCount(q);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#findOne(java.lang.String, java.lang.Object)
	 */
	public T findOne(final String key, final Object value) {
		return ds.find(entityClazz, key, value).get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#findOne(org.mongodb.morphia.query.Query)
	 */
	public T findOne(final Query<T> q) {
		return q.get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#find()
	 */
	public QueryResults<T> find() {
		return createQuery();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#find(org.mongodb.morphia.query.Query)
	 */
	public QueryResults<T> find(final Query<T> q) {
		return q;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mongodb.morphia.DAO#getDatastore()
	 */
	public Datastore getDatastore() {
		return ds;
	}

	public void ensureIndexes() {
		ds.ensureIndexes(entityClazz);
	}

}
