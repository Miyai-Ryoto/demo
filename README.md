# ⭐️ アプリ概要
### タイトル名：依頼ポータルサイト
Github：
**[https://github.com/Miyai-Ryoto/demo](https://github.com/Miyai-Ryoto/demo)**

## コンセプト
 - 自治体に向けた、庁内での依頼・照会を簡易に依頼・検索・確認できるWebアプリケーションです。
 - 特定の部署に属するユーザーに依頼し、回答状況を確認することができます。
 - 自分が属する部署の依頼処理状況及び担当者を確認することができます。

## 特徴
 - 依頼又は回答からのファイルアップロード
 - 処理状況、日付、キーワード、部署名からの依頼検索機能
 - 所属職員の担当者設定

<br>

## 使用画面のイメージ
### 画面遷移図
![Untitled](https://github.com/user-attachments/assets/76cedcc4-3768-48d7-aaf5-e24d0317808b)

### 依頼一覧画面
![requestlist](https://github.com/user-attachments/assets/3fc858f9-d4ad-4428-857d-7214ccc6a795)

### 依頼詳細画面
![requestdetail](https://github.com/user-attachments/assets/1c8322f8-94b6-49ca-a26f-6952ba356280)

### 回答詳細画面
![answerdetail](https://github.com/user-attachments/assets/a8ee860b-0325-4064-b32b-a6cd52b734f2)

### 仕事一覧画面
![tasklist](https://github.com/user-attachments/assets/336cd731-b447-4b25-9a79-1ca05ccf44c2)

### 仕事詳細画面
![taskdetail](https://github.com/user-attachments/assets/c0ba6ba0-4e01-4b97-a99b-906599204d84)

### 依頼作成画面
![post](https://github.com/user-attachments/assets/4565d22b-81c7-401b-8c69-203be37f926c)

<br>

# ⭐️ アプリの機能一覧
## メイン機能
 - 依頼及び回答の投稿機能（CRUD）
 - ファイルアップロード機能（ファイル形式 : docx, xlsx,）
 - ページネーション機能
 - 担当者の設定
 - 依頼検索機能（キーワード、回答状況、日付、部署名）

## 認証機能
 - ユーザー登録 / ログイン / ログアウト
 - パスワードのハッシュ化

 <br>

# ⭐️ 使用技術

### フロントエンド
- HTML / CSS / Tailwind CSS
- JavaScript

### バックエンド
- Java 21
- Spring Boot 3.3.5
- Spring Security 6
- MySQL 5.5
- JUnit 5
- Node.js 16.20.0 / npm 9.6.6

### インフラ

- Docker 23.0.5 / docker-compose 1.29.2（開発環境）
- Heroku
- CircleCI 2.1

<br>

# ⭐️ ER図
![ER図](https://github.com/user-attachments/assets/0e584865-780a-4001-a72a-51f96706921b)

<br>

# ⭐️ テーブル定義書
概要レベルのER図を元に、テーブル定義書を作成しました。

postsテーブル
textsテーブル
bookmarksテーブル
usersテーブル

<br>

# ⭐️ 工夫したところ
 - ユーザーの所属部署に合わせてリストを表示。
 - 回答時の依頼処理状況の変更。（未回答⇒回答済）
 - JUnitを取り入れたバグの検知。
 - 同部署のユーザー一覧からの担当者設定。

<br>

# ⭐️ 苦労したところ
 - Spring Securityの実装
 - Docker-Composeの起動（結果：実装できず）

<br>

# ⭐️ 今後について
 - インフラの整備
 - ⇒Docker-Composeを起動させる
 - ⇒AWSの導入
