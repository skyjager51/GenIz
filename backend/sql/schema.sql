BEGIN;

CREATE SCHEMA IF NOT EXISTS geniz;
USE geniz;

-- Table for user data 
CREATE TABLE IF NOT EXISTS users(
    id INT NOT NULL AUTO_INCREMENT,
    identity_provider_user_id VARCHAR(255) NOT NULL UNIQUE,
    use_local_model BOOLEAN,
    PRIMARY KEY (id)
);

-- Table for chats
CREATE TABLE IF NOT EXISTS chats(
    chat_id INT NOT NULL AUTO_INCREMENT,
    chat_name VARCHAR(50),
    user_id INT NOT NULL,
    PRIMARY KEY (chat_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Table for discussions 
CREATE TABLE IF NOT EXISTS discussions(
    discussion_id INT NOT NULL AUTO_INCREMENT,
    user_pdf_name VARCHAR(100),
    quiz_content TEXT NOT NULL,
    chat_id INT NOT NULL,
    PRIMARY KEY (discussion_id),
    FOREIGN KEY (chat_id) REFERENCES chats (chat_id) ON DELETE CASCADE
);

-- Table for online model values 
CREATE TABLE IF NOT EXISTS online_model(
    model_id INT NOT NULL AUTO_INCREMENT,
    model_url VARCHAR(2083) UNIQUE,
    model_name VARCHAR(200) UNIQUE,
    api_key VARCHAR(500) UNIQUE
);

COMMIT;