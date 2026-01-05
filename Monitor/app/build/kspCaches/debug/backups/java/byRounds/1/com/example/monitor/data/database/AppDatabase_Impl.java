package com.example.monitor.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.monitor.data.dao.CategoryDao;
import com.example.monitor.data.dao.CategoryDao_Impl;
import com.example.monitor.data.dao.CurrencyDao;
import com.example.monitor.data.dao.CurrencyDao_Impl;
import com.example.monitor.data.dao.ExpenseDao;
import com.example.monitor.data.dao.ExpenseDao_Impl;
import com.example.monitor.data.dao.NoteDao;
import com.example.monitor.data.dao.NoteDao_Impl;
import com.example.monitor.data.dao.ReminderDao;
import com.example.monitor.data.dao.ReminderDao_Impl;
import com.example.monitor.data.dao.ReportDao;
import com.example.monitor.data.dao.ReportDao_Impl;
import com.example.monitor.data.dao.UserDao;
import com.example.monitor.data.dao.UserDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserDao _userDao;

  private volatile CategoryDao _categoryDao;

  private volatile CurrencyDao _currencyDao;

  private volatile NoteDao _noteDao;

  private volatile ExpenseDao _expenseDao;

  private volatile ReminderDao _reminderDao;

  private volatile ReportDao _reportDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`userId` TEXT NOT NULL, `defaultCurrency` TEXT NOT NULL, `darkMode` INTEGER NOT NULL, PRIMARY KEY(`userId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `categories` (`categoryId` TEXT NOT NULL, `name` TEXT NOT NULL, `icon` TEXT NOT NULL, `color` TEXT NOT NULL, PRIMARY KEY(`categoryId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `currencies` (`code` TEXT NOT NULL, `rate` REAL NOT NULL, PRIMARY KEY(`code`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `notes` (`noteId` TEXT NOT NULL, `content` TEXT NOT NULL, PRIMARY KEY(`noteId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `expenses` (`expenseId` TEXT NOT NULL, `userId` TEXT NOT NULL, `amount` REAL NOT NULL, `date` INTEGER NOT NULL, `categoryId` TEXT NOT NULL, `currencyCode` TEXT NOT NULL, `noteId` TEXT, PRIMARY KEY(`expenseId`), FOREIGN KEY(`userId`) REFERENCES `users`(`userId`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`categoryId`) REFERENCES `categories`(`categoryId`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`currencyCode`) REFERENCES `currencies`(`code`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`noteId`) REFERENCES `notes`(`noteId`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_expenses_userId` ON `expenses` (`userId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_expenses_categoryId` ON `expenses` (`categoryId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_expenses_currencyCode` ON `expenses` (`currencyCode`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_expenses_noteId` ON `expenses` (`noteId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `reminders` (`reminderId` TEXT NOT NULL, `userId` TEXT NOT NULL, `date` INTEGER NOT NULL, `frequency` TEXT NOT NULL, PRIMARY KEY(`reminderId`), FOREIGN KEY(`userId`) REFERENCES `users`(`userId`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_reminders_userId` ON `reminders` (`userId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `reports` (`reportId` TEXT NOT NULL, `userId` TEXT NOT NULL, `period` TEXT NOT NULL, `filePath` TEXT, PRIMARY KEY(`reportId`), FOREIGN KEY(`userId`) REFERENCES `users`(`userId`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_reports_userId` ON `reports` (`userId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'daa57c2ba21be5046f1b5d505e14eda7')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `categories`");
        db.execSQL("DROP TABLE IF EXISTS `currencies`");
        db.execSQL("DROP TABLE IF EXISTS `notes`");
        db.execSQL("DROP TABLE IF EXISTS `expenses`");
        db.execSQL("DROP TABLE IF EXISTS `reminders`");
        db.execSQL("DROP TABLE IF EXISTS `reports`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(3);
        _columnsUsers.put("userId", new TableInfo.Column("userId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("defaultCurrency", new TableInfo.Column("defaultCurrency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("darkMode", new TableInfo.Column("darkMode", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.example.monitor.data.model.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsCategories = new HashMap<String, TableInfo.Column>(4);
        _columnsCategories.put("categoryId", new TableInfo.Column("categoryId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("icon", new TableInfo.Column("icon", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("color", new TableInfo.Column("color", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategories = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCategories = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCategories = new TableInfo("categories", _columnsCategories, _foreignKeysCategories, _indicesCategories);
        final TableInfo _existingCategories = TableInfo.read(db, "categories");
        if (!_infoCategories.equals(_existingCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "categories(com.example.monitor.data.model.Category).\n"
                  + " Expected:\n" + _infoCategories + "\n"
                  + " Found:\n" + _existingCategories);
        }
        final HashMap<String, TableInfo.Column> _columnsCurrencies = new HashMap<String, TableInfo.Column>(2);
        _columnsCurrencies.put("code", new TableInfo.Column("code", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurrencies.put("rate", new TableInfo.Column("rate", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCurrencies = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCurrencies = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCurrencies = new TableInfo("currencies", _columnsCurrencies, _foreignKeysCurrencies, _indicesCurrencies);
        final TableInfo _existingCurrencies = TableInfo.read(db, "currencies");
        if (!_infoCurrencies.equals(_existingCurrencies)) {
          return new RoomOpenHelper.ValidationResult(false, "currencies(com.example.monitor.data.model.Currency).\n"
                  + " Expected:\n" + _infoCurrencies + "\n"
                  + " Found:\n" + _existingCurrencies);
        }
        final HashMap<String, TableInfo.Column> _columnsNotes = new HashMap<String, TableInfo.Column>(2);
        _columnsNotes.put("noteId", new TableInfo.Column("noteId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNotes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNotes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNotes = new TableInfo("notes", _columnsNotes, _foreignKeysNotes, _indicesNotes);
        final TableInfo _existingNotes = TableInfo.read(db, "notes");
        if (!_infoNotes.equals(_existingNotes)) {
          return new RoomOpenHelper.ValidationResult(false, "notes(com.example.monitor.data.model.Note).\n"
                  + " Expected:\n" + _infoNotes + "\n"
                  + " Found:\n" + _existingNotes);
        }
        final HashMap<String, TableInfo.Column> _columnsExpenses = new HashMap<String, TableInfo.Column>(7);
        _columnsExpenses.put("expenseId", new TableInfo.Column("expenseId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("categoryId", new TableInfo.Column("categoryId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("currencyCode", new TableInfo.Column("currencyCode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("noteId", new TableInfo.Column("noteId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExpenses = new HashSet<TableInfo.ForeignKey>(4);
        _foreignKeysExpenses.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("userId")));
        _foreignKeysExpenses.add(new TableInfo.ForeignKey("categories", "CASCADE", "NO ACTION", Arrays.asList("categoryId"), Arrays.asList("categoryId")));
        _foreignKeysExpenses.add(new TableInfo.ForeignKey("currencies", "CASCADE", "NO ACTION", Arrays.asList("currencyCode"), Arrays.asList("code")));
        _foreignKeysExpenses.add(new TableInfo.ForeignKey("notes", "SET NULL", "NO ACTION", Arrays.asList("noteId"), Arrays.asList("noteId")));
        final HashSet<TableInfo.Index> _indicesExpenses = new HashSet<TableInfo.Index>(4);
        _indicesExpenses.add(new TableInfo.Index("index_expenses_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        _indicesExpenses.add(new TableInfo.Index("index_expenses_categoryId", false, Arrays.asList("categoryId"), Arrays.asList("ASC")));
        _indicesExpenses.add(new TableInfo.Index("index_expenses_currencyCode", false, Arrays.asList("currencyCode"), Arrays.asList("ASC")));
        _indicesExpenses.add(new TableInfo.Index("index_expenses_noteId", false, Arrays.asList("noteId"), Arrays.asList("ASC")));
        final TableInfo _infoExpenses = new TableInfo("expenses", _columnsExpenses, _foreignKeysExpenses, _indicesExpenses);
        final TableInfo _existingExpenses = TableInfo.read(db, "expenses");
        if (!_infoExpenses.equals(_existingExpenses)) {
          return new RoomOpenHelper.ValidationResult(false, "expenses(com.example.monitor.data.model.Expense).\n"
                  + " Expected:\n" + _infoExpenses + "\n"
                  + " Found:\n" + _existingExpenses);
        }
        final HashMap<String, TableInfo.Column> _columnsReminders = new HashMap<String, TableInfo.Column>(4);
        _columnsReminders.put("reminderId", new TableInfo.Column("reminderId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("frequency", new TableInfo.Column("frequency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReminders = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysReminders.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("userId")));
        final HashSet<TableInfo.Index> _indicesReminders = new HashSet<TableInfo.Index>(1);
        _indicesReminders.add(new TableInfo.Index("index_reminders_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        final TableInfo _infoReminders = new TableInfo("reminders", _columnsReminders, _foreignKeysReminders, _indicesReminders);
        final TableInfo _existingReminders = TableInfo.read(db, "reminders");
        if (!_infoReminders.equals(_existingReminders)) {
          return new RoomOpenHelper.ValidationResult(false, "reminders(com.example.monitor.data.model.Reminder).\n"
                  + " Expected:\n" + _infoReminders + "\n"
                  + " Found:\n" + _existingReminders);
        }
        final HashMap<String, TableInfo.Column> _columnsReports = new HashMap<String, TableInfo.Column>(4);
        _columnsReports.put("reportId", new TableInfo.Column("reportId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("period", new TableInfo.Column("period", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReports.put("filePath", new TableInfo.Column("filePath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReports = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysReports.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("userId")));
        final HashSet<TableInfo.Index> _indicesReports = new HashSet<TableInfo.Index>(1);
        _indicesReports.add(new TableInfo.Index("index_reports_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        final TableInfo _infoReports = new TableInfo("reports", _columnsReports, _foreignKeysReports, _indicesReports);
        final TableInfo _existingReports = TableInfo.read(db, "reports");
        if (!_infoReports.equals(_existingReports)) {
          return new RoomOpenHelper.ValidationResult(false, "reports(com.example.monitor.data.model.Report).\n"
                  + " Expected:\n" + _infoReports + "\n"
                  + " Found:\n" + _existingReports);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "daa57c2ba21be5046f1b5d505e14eda7", "f0970ba4dff6d53c341028e19968e1eb");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","categories","currencies","notes","expenses","reminders","reports");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `categories`");
      _db.execSQL("DELETE FROM `currencies`");
      _db.execSQL("DELETE FROM `notes`");
      _db.execSQL("DELETE FROM `expenses`");
      _db.execSQL("DELETE FROM `reminders`");
      _db.execSQL("DELETE FROM `reports`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CategoryDao.class, CategoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CurrencyDao.class, CurrencyDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NoteDao.class, NoteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ExpenseDao.class, ExpenseDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReminderDao.class, ReminderDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReportDao.class, ReportDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public CategoryDao categoryDao() {
    if (_categoryDao != null) {
      return _categoryDao;
    } else {
      synchronized(this) {
        if(_categoryDao == null) {
          _categoryDao = new CategoryDao_Impl(this);
        }
        return _categoryDao;
      }
    }
  }

  @Override
  public CurrencyDao currencyDao() {
    if (_currencyDao != null) {
      return _currencyDao;
    } else {
      synchronized(this) {
        if(_currencyDao == null) {
          _currencyDao = new CurrencyDao_Impl(this);
        }
        return _currencyDao;
      }
    }
  }

  @Override
  public NoteDao noteDao() {
    if (_noteDao != null) {
      return _noteDao;
    } else {
      synchronized(this) {
        if(_noteDao == null) {
          _noteDao = new NoteDao_Impl(this);
        }
        return _noteDao;
      }
    }
  }

  @Override
  public ExpenseDao expenseDao() {
    if (_expenseDao != null) {
      return _expenseDao;
    } else {
      synchronized(this) {
        if(_expenseDao == null) {
          _expenseDao = new ExpenseDao_Impl(this);
        }
        return _expenseDao;
      }
    }
  }

  @Override
  public ReminderDao reminderDao() {
    if (_reminderDao != null) {
      return _reminderDao;
    } else {
      synchronized(this) {
        if(_reminderDao == null) {
          _reminderDao = new ReminderDao_Impl(this);
        }
        return _reminderDao;
      }
    }
  }

  @Override
  public ReportDao reportDao() {
    if (_reportDao != null) {
      return _reportDao;
    } else {
      synchronized(this) {
        if(_reportDao == null) {
          _reportDao = new ReportDao_Impl(this);
        }
        return _reportDao;
      }
    }
  }
}
