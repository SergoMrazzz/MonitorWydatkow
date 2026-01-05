package com.example.monitor.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.monitor.data.model.Currency;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CurrencyDao_Impl implements CurrencyDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Currency> __insertionAdapterOfCurrency;

  private final EntityDeletionOrUpdateAdapter<Currency> __deletionAdapterOfCurrency;

  private final EntityDeletionOrUpdateAdapter<Currency> __updateAdapterOfCurrency;

  public CurrencyDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCurrency = new EntityInsertionAdapter<Currency>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `currencies` (`code`,`rate`) VALUES (?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Currency entity) {
        statement.bindString(1, entity.getCode());
        statement.bindDouble(2, entity.getRate());
      }
    };
    this.__deletionAdapterOfCurrency = new EntityDeletionOrUpdateAdapter<Currency>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `currencies` WHERE `code` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Currency entity) {
        statement.bindString(1, entity.getCode());
      }
    };
    this.__updateAdapterOfCurrency = new EntityDeletionOrUpdateAdapter<Currency>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `currencies` SET `code` = ?,`rate` = ? WHERE `code` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Currency entity) {
        statement.bindString(1, entity.getCode());
        statement.bindDouble(2, entity.getRate());
        statement.bindString(3, entity.getCode());
      }
    };
  }

  @Override
  public Object insertCurrency(final Currency currency,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCurrency.insert(currency);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAllCurrencies(final List<Currency> currencies,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCurrency.insert(currencies);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCurrency(final Currency currency,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCurrency.handle(currency);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCurrency(final Currency currency,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCurrency.handle(currency);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<Currency> getCurrencyByCode(final String code) {
    final String _sql = "SELECT * FROM currencies WHERE code = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, code);
    return __db.getInvalidationTracker().createLiveData(new String[] {"currencies"}, false, new Callable<Currency>() {
      @Override
      @Nullable
      public Currency call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
          final int _cursorIndexOfRate = CursorUtil.getColumnIndexOrThrow(_cursor, "rate");
          final Currency _result;
          if (_cursor.moveToFirst()) {
            final String _tmpCode;
            _tmpCode = _cursor.getString(_cursorIndexOfCode);
            final double _tmpRate;
            _tmpRate = _cursor.getDouble(_cursorIndexOfRate);
            _result = new Currency(_tmpCode,_tmpRate);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Currency>> getAllCurrencies() {
    final String _sql = "SELECT * FROM currencies";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"currencies"}, false, new Callable<List<Currency>>() {
      @Override
      @Nullable
      public List<Currency> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
          final int _cursorIndexOfRate = CursorUtil.getColumnIndexOrThrow(_cursor, "rate");
          final List<Currency> _result = new ArrayList<Currency>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Currency _item;
            final String _tmpCode;
            _tmpCode = _cursor.getString(_cursorIndexOfCode);
            final double _tmpRate;
            _tmpRate = _cursor.getDouble(_cursorIndexOfRate);
            _item = new Currency(_tmpCode,_tmpRate);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
