/**
 * Licensed to JumpMind Inc under one or more contributor
 * license agreements.  See the NOTICE file distributed
 * with this work for additional information regarding
 * copyright ownership.  JumpMind Inc licenses this file
 * to you under the GNU General Public License, version 3.0 (GPLv3)
 * (the "License"); you may not use this file except in compliance
 * with the License.
 *
 * You should have received a copy of the GNU General Public License,
 * version 3.0 (GPLv3) along with this library; if not, see
 * <http://www.gnu.org/licenses/>.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jumpmind.symmetric.db.raima;

import org.jumpmind.db.platform.IDatabasePlatform;
import org.jumpmind.db.platform.PermissionType;
import org.jumpmind.db.sql.ISqlTransaction;
import org.jumpmind.db.util.BinaryEncoding;
import org.jumpmind.symmetric.db.AbstractSymmetricDialect;
import org.jumpmind.symmetric.db.ISymmetricDialect;
import org.jumpmind.symmetric.service.IParameterService;

public class RaimaSymmetricDialect extends AbstractSymmetricDialect implements ISymmetricDialect {

    public RaimaSymmetricDialect(IParameterService parameterService, IDatabasePlatform platform) {
        super(parameterService, platform);
        this.triggerTemplate = new RaimaTriggerTemplate(this);
        this.parameterService = parameterService;                     
    }

    @Override
    public void createRequiredDatabaseObjects() {
    }

    @Override
    public void dropRequiredDatabaseObjects() {
    }

    @Override
    public boolean supportsTransactionId() {
        return false;
    }

    @Override
    protected boolean doesTriggerExistOnPlatform(String catalog, String schema, String tableName,
            String triggerName) {
        /*
        schema = schema == null ? (platform.getDefaultSchema() == null ? null : platform
                .getDefaultSchema()) : schema;
        String checkSchemaSql = (schema != null && schema.length() > 0) ? " and schema='"
                + schema + "'"
                : "";
        return platform
                .getSqlTemplate()
                .queryForInt(
                        "select count(*) from system.triggers where triggername = ? and tablename = ?"
                                + checkSchemaSql, new Object[] { triggerName, tableName }) > 0;
         */
        return true;
    }

    @Override
    public void removeTrigger(StringBuilder sqlBuffer, String catalogName, String schemaName,
            String triggerName, String tableName) {
        /*
        final String sql = "drop trigger " + triggerName;
        logSql(sql, sqlBuffer); 
        if (parameterService.is(ParameterConstants.AUTO_SYNC_TRIGGERS)) {
            try {
                platform.getSqlTemplate().update(sql);
            } catch (Exception e) {
                log.warn("Trigger does not exist");
            }
        }
        */
    }

    public void disableSyncTriggers(ISqlTransaction transaction, String nodeId) {
        /*
        transaction.prepareAndExecute("select " + this.parameterService.getTablePrefix() + "_set_session_variable('" + SYNC_TRIGGERS_DISABLED_USER_VARIABLE + "', '1') from dual");
        if (nodeId != null) {
            transaction
                    .prepareAndExecute("select " + this.parameterService.getTablePrefix()+ "_set_session_variable('" + SYNC_TRIGGERS_DISABLED_NODE_VARIABLE + "','" + nodeId + "') from dual");
        }
        */
    }

    public void enableSyncTriggers(ISqlTransaction transaction) {
        /*
        transaction.prepareAndExecute("select " + this.parameterService.getTablePrefix() + "_set_session_variable('" + SYNC_TRIGGERS_DISABLED_USER_VARIABLE + "', null) from dual");
        transaction.prepareAndExecute("select " + this.parameterService.getTablePrefix() + "_set_session_variable('" + SYNC_TRIGGERS_DISABLED_NODE_VARIABLE + "', null) from dual");
        */
    }

    public String getSyncTriggersExpression() {
        //return this.parameterService.getTablePrefix()+ "_get_session_variable('" + SYNC_TRIGGERS_DISABLED_USER_VARIABLE + "') is null";
        return "1=1";
    }

    public void cleanDatabase() {
    }

    @Override
    public boolean isClobSyncSupported() {
        return false;
    }

    @Override
    public boolean isBlobSyncSupported() {
        return false;
    }

    @Override
    public BinaryEncoding getBinaryEncoding() {
        return BinaryEncoding.NONE;
    }
    
    @Override
    public PermissionType[] getSymTablePermissions() {
        PermissionType[] permissions = { PermissionType.CREATE_TABLE, PermissionType.DROP_TABLE, PermissionType.CREATE_TRIGGER, PermissionType.DROP_TRIGGER};
        return permissions;
    }

}

