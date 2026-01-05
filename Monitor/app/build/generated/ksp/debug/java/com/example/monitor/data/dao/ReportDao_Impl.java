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
import com.example.monitor.data.model.Report;
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
public final class ReportDao_Impl implements ReportDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Report> __insertionAdapterOfReport;

  private final EntityDeletionOrUpdateAdapter<Report> __deletionAdapterOfReport;

  private final EntityDeletionOrUpdateAdapter<Report> __updateAdapterOfReport;

  public ReportDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReport = new EntityInsertionAdapter<Report>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `reports` (`reportId`,`userId`,`period`,`filePath`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Report entity) {
        statement.bindString(1, entity.getReportId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getPeriod());
        if (entity.getFilePath() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getFilePath());
        }
      }
    };
    this.__deletionAdapterOfReport = new EntityDeletionOrUpdateAdapter<Report>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `reports` WHERE `reportId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Report entity) {
        statement.bindString(1, entity.getReportId());
      }
    };
    this.__updateAdapterOfReport = new EntityDeletionOrUpdateAdapter<Report>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `reports` SET `reportId` = ?,`userId` = ?,`period` = ?,`filePath` = ? WHERE `reportId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Report entity) {
        statement.bindString(1, entity.getReportId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getPeriod());
        if (entity.getFilePath() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getFilePath());
        }
        statement.bindString(5, entity.getReportId());
      }
    };
  }

  @Override
  public Object insertReport(final Report report, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfReport.insert(report);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteReport(final Report report, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfReport.handle(report);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateReport(final Report report, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfReport.handle(report);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<Report> getReportById(final String reportId) {
    final String _sql = "SELECT * FROM reports WHERE reportId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, reportId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"reports"}, false, new Callable<Report>() {
      @Override
      @Nullable
      public Report call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "reportId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfPeriod = CursorUtil.getColumnIndexOrThrow(_cursor, "period");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final Report _result;
          if (_cursor.moveToFirst()) {
            final String _tmpReportId;
            _tmpReportId = _cursor.getString(_cursorIndexOfReportId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpPeriod;
            _tmpPeriod = _cursor.getString(_cursorIndexOfPeriod);
            final String _tmpFilePath;
            if (_cursor.isNull(_cursorIndexOfFilePath)) {
              _tmpFilePath = null;
            } else {
              _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            }
            _result = new Report(_tmpReportId,_tmpUserId,_tmpPeriod,_tmpFilePath);
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
  public LiveData<List<Report>> getReportsByUserId(final String userId) {
    final String _sql = "SELECT * FROM reports WHERE userId = ? ORDER BY reportId DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"reports"}, false, new Callable<List<Report>>() {
      @Override
      @Nullable
      public List<Report> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "reportId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfPeriod = CursorUtil.getColumnIndexOrThrow(_cursor, "period");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final List<Report> _result = new ArrayList<Report>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Report _item;
            final String _tmpReportId;
            _tmpReportId = _cursor.getString(_cursorIndexOfReportId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpPeriod;
            _tmpPeriod = _cursor.getString(_cursorIndexOfPeriod);
            final String _tmpFilePath;
            if (_cursor.isNull(_cursorIndexOfFilePath)) {
              _tmpFilePath = null;
            } else {
              _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            }
            _item = new Report(_tmpReportId,_tmpUserId,_tmpPeriod,_tmpFilePath);
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
