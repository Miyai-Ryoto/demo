package com.example.demo.constant;

public class UrlConst {

    /** ログイン画面 */
	public static final String LOGIN = "/login";

	/** ユーザー登録画面 */
	public static final String SIGNUP = "/signup";

	/** フォーム画面 */
	public static final String POST = "/post";

	/** 一覧画面 */
	public static final String TASKLIST = "/tasklist";

	/** 検索画面 */
	public static final String TASKSEARCH = "/tasklist/search";

	/** 詳細画面 */
	public static final String TASKDETAIL = "/taskdetail/{id}";

	/** 依頼画面 */
	public static final String REQUESTLIST = "/requestlist";

	/** 検索画面 */
	public static final String REQUESTSEARCH = "/requestlist/search";

	/** 依頼詳細画面 */
	public static final String REQUESTDETAIL = "/requestdetail/{id}";

	/** 回答 */
	public static final String ANSWER = "/tasklist/{id}/answer";

	/** 回答詳細画面 */
	public static final String ANSWERDETAIL = "/answerdetail/{id}";

	/** 担当者設定 */
	public static final String RESPONSIBLE = "/tasklist/{id}/responsible";

	/** 依頼添付ファイルダウンロード */
	public static final String REQUESTFILE = "/requestFile/{id}";

	/** 回答添付ファイルダウンロード */
	public static final String ANSWERFILE = "/answerFile/{id}";

	/** 認証不要画面 */
	public static final String[] NO_AUTHENTICATION = { LOGIN, SIGNUP,"/output.css" };

}
