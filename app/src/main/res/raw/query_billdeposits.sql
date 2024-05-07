WITH RECURSIVE categories(categid, categname, parentid) AS
    (SELECT a.categid, a.categname, a.parentid FROM category_v1 a WHERE parentid = '-1'
        UNION ALL
     SELECT c.categid, r.categname || ':' || c.categname, c.parentid
     FROM categories r, category_v1 c
	 WHERE r.categid = c.parentid
	 )
SELECT
    BILLSDEPOSITS_V1.BDID,
    BILLSDEPOSITS_V1.PAYEEID,
    PAYEE_V1.PAYEENAME,
    BILLSDEPOSITS_V1.TOACCOUNTID,
    TOACCOUNT.ACCOUNTNAME AS TOACCOUNTNAME,
    BILLSDEPOSITS_V1.ACCOUNTID,
    ACCOUNTLIST_V1.ACCOUNTNAME,
    ACCOUNTLIST_V1.CURRENCYID,
    NULL AS SUBCATEGNAME,
    categories.CATEGNAME AS CATEGNAME,
    BILLSDEPOSITS_V1.TRANSCODE,
    BILLSDEPOSITS_V1.TRANSAMOUNT,
    BILLSDEPOSITS_V1.NEXTOCCURRENCEDATE,
    BILLSDEPOSITS_V1.REPEATS,
    julianday(BILLSDEPOSITS_V1.NEXTOCCURRENCEDATE) - julianday(date('now')) AS DAYSLEFT,
    BILLSDEPOSITS_V1.NOTES,
    BILLSDEPOSITS_V1.STATUS,
    BILLSDEPOSITS_V1.NUMOCCURRENCES,
    BILLSDEPOSITS_V1.TOTRANSAMOUNT,
    BILLSDEPOSITS_V1.TRANSACTIONNUMBER,
    BILLSDEPOSITS_V1.TRANSDATE,
    ( CASE BILLSDEPOSITS_V1.TRANSCODE WHEN 'Withdrawal' THEN -1 ELSE 1 END ) * BILLSDEPOSITS_V1.TRANSAMOUNT AS AMOUNT,
    ifnull( ifnull( strftime( df.infovalue, BILLSDEPOSITS_V1.NEXTOCCURRENCEDATE ) ,
        ( strftime( REPLACE( df.infovalue, '%y', SubStr( strftime( '%Y', BILLSDEPOSITS_V1.NEXTOCCURRENCEDATE ) , 3, 2 )  ) , BILLSDEPOSITS_V1.NEXTOCCURRENCEDATE )  )  ) ,
        date( BILLSDEPOSITS_V1.NEXTOCCURRENCEDATE )  ) AS USERNEXTOCCURRENCEDATE
FROM BILLSDEPOSITS_V1 LEFT OUTER JOIN PAYEE_V1 ON BILLSDEPOSITS_V1.PAYEEID = PAYEE_V1.PAYEEID
    LEFT OUTER JOIN ACCOUNTLIST_V1 TOACCOUNT ON BILLSDEPOSITS_V1.TOACCOUNTID = TOACCOUNT.ACCOUNTID
    LEFT OUTER JOIN categories ON BILLSDEPOSITS_V1.CATEGID = categories.CATEGID
    -- LEFT JOIN CATEGORY_V1 PARENTCAT ON PARENTCAT.CATEGID = CATEGORY_V1.PARENTID
    LEFT OUTER JOIN INFOTABLE_V1 DF ON DF.INFONAME = 'DATEFORMAT', ACCOUNTLIST_V1
WHERE BILLSDEPOSITS_V1.ACCOUNTID = ACCOUNTLIST_V1.ACCOUNTID
