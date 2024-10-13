package com.example.demo.constant;

public class UrlConst {

    /** ログイン画面 */
	public static final String LOGIN = "/login";

	/** ユーザー登録画面 */
	public static final String SIGNUP = "/signup";

	/** メニュー画面 */
	public static final String MENU = "/menu";

	/** フォーム画面 */
	public static final String POST = "/post";

	/** 一覧画面 */
	public static final String LIST = "/list";

	/** 検索画面 */
	public static final String SEARCH = "/list/search";

	/** 検索結果画面 */
	public static final String RESULT = "/list/search/resurt";

	/** 詳細画面 */
	public static final String DETAIL = "/list/{id}";

	/** 依頼画面 */
	public static final String REQUEST = "/request";

	/** 回答画面 */
	public static final String ANSWER = "/list/{id}/answer";

	/** 担当者設定画面 */
	public static final String RESPONSIBLE = "/list/{id}/responsible";

	/** 認証不要画面 */
	public static final String[] NO_AUTHENTICATION = { LOGIN, SIGNUP,"/output.css" };

}
