# ⭐️ アプリ概要
### タイトル名：依頼・回答アプリ
アプリ：
**[https://spring-ryt-de03441b2cf9.herokuapp.com/signup](https://spring-ryt-de03441b2cf9.herokuapp.com/signup)**

## コンセプト
 - 自治体での政策や計画策定時に必要となるデータを各課で共有するためのアプリです。
 - 短時間で特定部署への依頼、及び回答を行うことができます。

## 特徴
 - 依頼又は回答からのファイルアップロード
 - 処理状況、日付、キーワード、部署名からの依頼検索機能
 - 所属職員の担当者設定

<br>

## 使用画面のイメージ
### 画面遷移図
![Untitled](https://github.com/user-attachments/assets/76cedcc4-3768-48d7-aaf5-e24d0317808b)

### 依頼一覧画面
ユーザーが作成した依頼の一覧を表示します。<br>
検索フォームでキーワード、日付から絞り込み検索をすることができます。
![requestlist](https://github.com/user-attachments/assets/3fc858f9-d4ad-4428-857d-7214ccc6a795)

### 依頼詳細画面
ユーザーが作成した依頼の詳細を表示します。<br>
他部署からの回答の一覧を表示します。
![requestdetail](https://github.com/user-attachments/assets/1c8322f8-94b6-49ca-a26f-6952ba356280)

### 回答詳細画面
他部署からの回答の詳細を表示します。<br>
ここでファイルのダウンロードが可能です。
![answerdetail](https://github.com/user-attachments/assets/a8ee860b-0325-4064-b32b-a6cd52b734f2)

### 仕事一覧画面
他部署から依頼された仕事の一覧を表示します。<br>
検索フォームでキーワード、処理状況、依頼元の部署、日付から絞り込み検索をすることができます。
![tasklist](https://github.com/user-attachments/assets/336cd731-b447-4b25-9a79-1ca05ccf44c2)

### 仕事詳細画面
他部署から依頼された仕事の詳細を表示します。<br>
ここで依頼への回答・担当者設定・ファイルのダウンロードが可能です。
![taskdetail](https://github.com/user-attachments/assets/c0ba6ba0-4e01-4b97-a99b-906599204d84)

### 依頼作成画面
依頼の作成・送信ができます。
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
- Spring Boot 3.3.3
- Spring Security 6
- MySQL 5.7
- JUnit 5
- Node.js 20.17.0 / npm 10.8.2

### インフラ

- Docker 27.1.1 / docker-compose 2.29.1（開発環境）
- Heroku

<br>

# ⭐️ ER図
![ER図](https://github.com/user-attachments/assets/0e584865-780a-4001-a72a-51f96706921b)

<br>

# ⭐️ テーブル定義書

部署テーブル
![部署](https://github.com/user-attachments/assets/83447d60-b418-4901-9d7d-17c80b6ac1f2)
社員テーブル
![社員](https://github.com/user-attachments/assets/9bb6e7d7-e3b4-4944-9c13-8c9ac87cf925)
依頼（原案）テーブル
![依頼（原案）](https://github.com/user-attachments/assets/f9931844-a72f-4763-a5e8-34078deb2349)
依頼（送付）テーブル
![依頼（送付）](https://github.com/user-attachments/assets/ccb30da2-c01d-4ceb-89dc-633d17ff87fa)
回答テーブル
![回答](https://github.com/user-attachments/assets/49b42345-800f-4ce4-b2bc-777b4b248848)
担当者テーブル
![担当者](https://github.com/user-attachments/assets/89ebae9b-c2c3-4247-84ca-24fc857bb7e9)

<br>

# ⭐️ 工夫したところ
 - 担当者の設定と回答の状況（回答済/未回答）を表示することで、同じ部署に属する職員と依頼の処理状態を常に共有できます。
 - 実際の現場を想定し、同じ部署に所属する職員であれば、本人以外にも担当者として設定することができます。
 - 依頼に回答した際、処理の状態が未回答⇒回答済へと自動で変換されます。

<br>

# ⭐️ 苦労したところ
 - DockerでSpringBootとMySQLの各コンテナを起動することはできたが、Docker-Composeの起動時にはコンテナ同士の連携ができなかった。

<br>

# ⭐️ 今後について
 - ▶インフラの強化
 - Docker-Composeの起動/AWSの導入
 - ▶機能の追加
 - リアルタイム通知（各ユーザーごとに未読/既読を表示する）
