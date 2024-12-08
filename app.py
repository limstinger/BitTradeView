from flask import Flask, jsonify
import sqlite3

app = Flask(__name__)

def get_db_connection():
    """데이터베이스 연결을 설정하고 커넥션 객체를 반환합니다."""
    conn = sqlite3.connect('bitcoin_trades.db')
    conn.row_factory = sqlite3.Row  # 조회 결과를 사전 형태로 반환하게 설정
    return conn

@app.route('/trades', methods=['GET'])
def get_trades():
    """거래 데이터를 조회하여 JSON 형식으로 반환하는 API 엔드포인트입니다."""
    conn = get_db_connection()
    cur = conn.cursor()
    cur.execute('SELECT * FROM trades')
    rows = cur.fetchall()
    cur.close()
    conn.close()
    
    # 조회된 데이터를 사전 리스트로 변환
    trades = [dict(row) for row in rows]
    return jsonify(trades)

if __name__ == '__main__':
    # 외부 접속을 허용하고, 디버그 모드에서 애플리케이션을 실행합니다.
    app.run(debug=False, host='0.0.0.0')
