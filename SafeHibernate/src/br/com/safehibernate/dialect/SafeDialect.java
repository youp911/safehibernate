package br.com.safehibernate.dialect;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.MappingException;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.lock.LockingStrategy;
import org.hibernate.exception.SQLExceptionConverter;
import org.hibernate.exception.ViolatedConstraintNameExtracter;
import org.hibernate.persister.entity.Lockable;
import org.hibernate.sql.CaseFragment;
import org.hibernate.sql.JoinFragment;

import br.com.safehibernate.CustomProperties;

public class SafeDialect extends Dialect {

	private Dialect inner;

	public SafeDialect() {
				
		
		String className = Environment.getProperties().getProperty( CustomProperties.WRAPPED_DIALECT );
		Class<?> clazz;
		try {
			if (className == null || className.trim().equals("")) {
				throw new IllegalArgumentException();
			}
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(CustomProperties.WRAPPED_DIALECT + " not found");
		}
		try {
			this.inner = (Dialect) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("unable to load " + className + " class");
		}
	}

	@Override
	public String appendIdentitySelectToInsert(String insertString) {
		return this.inner.appendIdentitySelectToInsert(insertString);
	}

	@Override
	public String appendLockHint(LockMode mode, String tableName) {
		return this.inner.appendLockHint(mode, tableName);
	}

	@Override
	public String applyLocksToSql(String sql,
		Map aliasedLockModes,
		Map keyColumnNames) {
		return this.inner.applyLocksToSql(sql, aliasedLockModes, keyColumnNames);
	}

	@Override
	public boolean areStringComparisonsCaseInsensitive() {
		return this.inner.areStringComparisonsCaseInsensitive();
	}

	@Override
	public boolean bindLimitParametersFirst() {
		return this.inner.bindLimitParametersFirst();
	}

	@Override
	public boolean bindLimitParametersInReverseOrder() {
		return this.inner.bindLimitParametersInReverseOrder();
	}

	@Override
	public SQLExceptionConverter buildSQLExceptionConverter() {
		return this.inner.buildSQLExceptionConverter();
	}

	@Override
	public char closeQuote() {
		return this.inner.closeQuote();
	}

	@Override
	public CaseFragment createCaseFragment() {
		return this.inner.createCaseFragment();
	}

	@Override
	public JoinFragment createOuterJoinFragment() {
		return this.inner.createOuterJoinFragment();
	}

	@Override
	public boolean doesReadCommittedCauseWritersToBlockReaders() {
		return this.inner.doesReadCommittedCauseWritersToBlockReaders();
	}

	@Override
	public boolean doesRepeatableReadCauseReadersToBlockWriters() {
		return this.inner.doesRepeatableReadCauseReadersToBlockWriters();
	}

	public boolean dropConstraints() {
		return inner.dropConstraints();
	}

	public boolean dropTemporaryTableAfterUse() {
		return inner.dropTemporaryTableAfterUse();
	}

	public boolean equals(Object obj) {
		return inner.equals(obj);
	}

	public boolean forUpdateOfColumns() {
		return inner.forUpdateOfColumns();
	}

	public String generateTemporaryTableName(String baseTableName) {
		return inner.generateTemporaryTableName(baseTableName);
	}

	public String getAddColumnString() {
		return inner.getAddColumnString();
	}

	public String getAddForeignKeyConstraintString(String constraintName,
		String[] foreignKey,
		String referencedTable,
		String[] primaryKey,
		boolean referencesPrimaryKey) {
		return inner.getAddForeignKeyConstraintString(constraintName,
				foreignKey, referencedTable, primaryKey, referencesPrimaryKey);
	}

	public String getAddPrimaryKeyConstraintString(String constraintName) {
		return inner.getAddPrimaryKeyConstraintString(constraintName);
	}

	public String getCascadeConstraintsString() {
		return inner.getCascadeConstraintsString();
	}

	public String getCastTypeName(int code) {
		return inner.getCastTypeName(code);
	}

	public String getColumnComment(String comment) {
		return inner.getColumnComment(comment);
	}

	public String getCreateMultisetTableString() {
		return inner.getCreateMultisetTableString();
	}

	public String[] getCreateSequenceStrings(String sequenceName,
		int initialValue,
		int incrementSize) throws MappingException {
		return inner.getCreateSequenceStrings(sequenceName, initialValue,
				incrementSize);
	}

	public String[] getCreateSequenceStrings(String sequenceName)
			throws MappingException {
		return inner.getCreateSequenceStrings(sequenceName);
	}

	public String getCreateTableString() {
		return inner.getCreateTableString();
	}

	public String getCreateTemporaryTablePostfix() {
		return inner.getCreateTemporaryTablePostfix();
	}

	public String getCreateTemporaryTableString() {
		return inner.getCreateTemporaryTableString();
	}

	public String getCurrentTimestampSelectString() {
		return inner.getCurrentTimestampSelectString();
	}

	public String getCurrentTimestampSQLFunctionName() {
		return inner.getCurrentTimestampSQLFunctionName();
	}

	public String getDropForeignKeyString() {
		return inner.getDropForeignKeyString();
	}

	public String[] getDropSequenceStrings(String sequenceName)
			throws MappingException {
		return inner.getDropSequenceStrings(sequenceName);
	}

	public String getForUpdateNowaitString() {
		return inner.getForUpdateNowaitString();
	}

	public String getForUpdateNowaitString(String aliases) {
		return inner.getForUpdateNowaitString(aliases);
	}

	public String getForUpdateString() {
		return inner.getForUpdateString();
	}

	public String getForUpdateString(LockMode lockMode) {
		return inner.getForUpdateString(lockMode);
	}

	public String getForUpdateString(String aliases) {
		return inner.getForUpdateString(aliases);
	}

	public String getHibernateTypeName(int code,
		int length,
		int precision,
		int scale) throws HibernateException {
		return inner.getHibernateTypeName(code, length, precision, scale);
	}

	public String getHibernateTypeName(int code) throws HibernateException {
		return inner.getHibernateTypeName(code);
	}

	public String getIdentityColumnString(int type) throws MappingException {
		return inner.getIdentityColumnString(type);
	}

	public String getIdentityInsertString() {
		return inner.getIdentityInsertString();
	}

	public String getIdentitySelectString(String table, String column, int type)
			throws MappingException {
		return inner.getIdentitySelectString(table, column, type);
	}

	public Set getKeywords() {
		return inner.getKeywords();
	}

	public String getLimitString(String query, int offset, int limit) {
		return inner.getLimitString(query, offset, limit);
	}

	public LockingStrategy getLockingStrategy(Lockable lockable,
		LockMode lockMode) {
		return inner.getLockingStrategy(lockable, lockMode);
	}

	public String getLowercaseFunction() {
		return inner.getLowercaseFunction();
	}

	public int getMaxAliasLength() {
		return inner.getMaxAliasLength();
	}

	public Class getNativeIdentifierGeneratorClass() {
		return inner.getNativeIdentifierGeneratorClass();
	}

	public String getNoColumnsInsertString() {
		return inner.getNoColumnsInsertString();
	}

	public String getNullColumnString() {
		return inner.getNullColumnString();
	}

	public String getQuerySequencesString() {
		return inner.getQuerySequencesString();
	}

	public ResultSet getResultSet(CallableStatement statement)
			throws SQLException {
		return inner.getResultSet(statement);
	}

	public String getSelectClauseNullString(int sqlType) {
		return inner.getSelectClauseNullString(sqlType);
	}

	public String getSelectGUIDString() {
		return inner.getSelectGUIDString();
	}

	public String getSelectSequenceNextValString(String sequenceName)
			throws MappingException {
		return inner.getSelectSequenceNextValString(sequenceName);
	}

	public String getSequenceNextValString(String sequenceName)
			throws MappingException {
		return inner.getSequenceNextValString(sequenceName);
	}

	public String getTableComment(String comment) {
		return inner.getTableComment(comment);
	}

	public String getTableTypeString() {
		return inner.getTableTypeString();
	}

	public String getTypeName(int code, int length, int precision, int scale)
			throws HibernateException {
		return inner.getTypeName(code, length, precision, scale);
	}

	public String getTypeName(int code) throws HibernateException {
		return inner.getTypeName(code);
	}

	public ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter() {
		return inner.getViolatedConstraintNameExtracter();
	}

	public boolean hasAlterTable() {
		return inner.hasAlterTable();
	}

	public boolean hasDataTypeInIdentityColumn() {
		return inner.hasDataTypeInIdentityColumn();
	}

	public int hashCode() {
		return inner.hashCode();
	}

	public boolean hasSelfReferentialForeignKeyBug() {
		return inner.hasSelfReferentialForeignKeyBug();
	}

	public boolean isCurrentTimestampSelectStringCallable() {
		return inner.isCurrentTimestampSelectStringCallable();
	}

	public char openQuote() {
		return inner.openQuote();
	}

	public Boolean performTemporaryTableDDLInIsolation() {
		return inner.performTemporaryTableDDLInIsolation();
	}

	public boolean qualifyIndexName() {
		return inner.qualifyIndexName();
	}

	public int registerResultSetOutParameter(CallableStatement statement,
		int position) throws SQLException {
		return inner.registerResultSetOutParameter(statement, position);
	}

	public boolean supportsBindAsCallableArgument() {
		return inner.supportsBindAsCallableArgument();
	}

	public boolean supportsCascadeDelete() {
		return inner.supportsCascadeDelete();
	}

	public boolean supportsCircularCascadeDeleteConstraints() {
		return inner.supportsCircularCascadeDeleteConstraints();
	}

	public boolean supportsColumnCheck() {
		return inner.supportsColumnCheck();
	}

	public boolean supportsCommentOn() {
		return inner.supportsCommentOn();
	}

	public boolean supportsCurrentTimestampSelection() {
		return inner.supportsCurrentTimestampSelection();
	}

	public boolean supportsEmptyInList() {
		return inner.supportsEmptyInList();
	}

	public boolean supportsExistsInSelect() {
		return inner.supportsExistsInSelect();
	}

	public boolean supportsExpectedLobUsagePattern() {
		return inner.supportsExpectedLobUsagePattern();
	}

	public boolean supportsIdentityColumns() {
		return inner.supportsIdentityColumns();
	}

	public boolean supportsIfExistsAfterTableName() {
		return inner.supportsIfExistsAfterTableName();
	}

	public boolean supportsIfExistsBeforeTableName() {
		return inner.supportsIfExistsBeforeTableName();
	}

	public boolean supportsInsertSelectIdentity() {
		return inner.supportsInsertSelectIdentity();
	}

	public boolean supportsLimit() {
		return inner.supportsLimit();
	}

	public boolean supportsLimitOffset() {
		return inner.supportsLimitOffset();
	}

	public boolean supportsLobValueChangePropogation() {
		return inner.supportsLobValueChangePropogation();
	}

	public boolean supportsNotNullUnique() {
		return inner.supportsNotNullUnique();
	}

	public boolean supportsOuterJoinForUpdate() {
		return inner.supportsOuterJoinForUpdate();
	}

	public boolean supportsParametersInInsertSelect() {
		return inner.supportsParametersInInsertSelect();
	}

	public boolean supportsPooledSequences() {
		return inner.supportsPooledSequences();
	}

	public boolean supportsResultSetPositionQueryMethodsOnForwardOnlyCursor() {
		return inner.supportsResultSetPositionQueryMethodsOnForwardOnlyCursor();
	}

	public boolean supportsRowValueConstructorSyntax() {
		return inner.supportsRowValueConstructorSyntax();
	}

	public boolean supportsRowValueConstructorSyntaxInInList() {
		return inner.supportsRowValueConstructorSyntaxInInList();
	}

	public boolean supportsSequences() {
		return inner.supportsSequences();
	}

	public boolean supportsSubqueryOnMutatingTable() {
		return inner.supportsSubqueryOnMutatingTable();
	}

	public boolean supportsSubselectAsInPredicateLHS() {
		return inner.supportsSubselectAsInPredicateLHS();
	}

	public boolean supportsTableCheck() {
		return inner.supportsTableCheck();
	}

	public boolean supportsTemporaryTables() {
		return inner.supportsTemporaryTables();
	}

	public boolean supportsUnboundedLobLocatorMaterialization() {
		return inner.supportsUnboundedLobLocatorMaterialization();
	}

	public boolean supportsUnionAll() {
		return inner.supportsUnionAll();
	}

	public boolean supportsUnique() {
		return inner.supportsUnique();
	}

	public boolean supportsUniqueConstraintInCreateAlterTable() {
		return inner.supportsUniqueConstraintInCreateAlterTable();
	}

	public boolean supportsVariableLimit() {
		return inner.supportsVariableLimit();
	}

	public String toBooleanValueString(boolean bool) {
		return inner.toBooleanValueString(bool);
	}

	public String transformSelectString(String select) {
		return inner.transformSelectString(select);
	}

	public boolean useInputStreamToInsertBlob() {
		return inner.useInputStreamToInsertBlob();
	}

	public boolean useMaxForLimit() {
		return inner.useMaxForLimit();
	}
	
	
	
}
