/**
 *
 */
package jp.data.exception;

import java.io.IOException;

/**
 * 適当なバーコードラベルを読みとれなかった時にスローされる例外
 * @author kentaroyoshida
 *
 */
public class BarcodeReaderException extends IOException {
	//warningを回避するための宣言
	private static final long serialVersionUID = 1L;

	/**
	 * @param message エラーメッセージ
	 */
	public BarcodeReaderException(String message) {
		super(message);
		// TODO 自動生成されたコンストラクター・スタブ
	}


}
