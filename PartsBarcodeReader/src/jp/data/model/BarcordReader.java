/**
 *
 */
package jp.data.model;

import jp.data.exception.BarcodeReaderException;

/**
 * 部品のバーコードから各種情報を読み込みます<br>
 * （バーコードタイプ、読み込み行、部品コード、個数、シリアル、メーカーコード）
 * @author kentaroyoshida
 *
 */
public class BarcordReader {
	private int barcodeLine;
	private String barcodeText;
	private int barcodeType;
	private String makerCode;
	private String partsCode;
	private int pcs;
	private String serial;

	/**
	 * デフォルトコンストラクタ
	 */
	public BarcordReader() {}

	/**
	 * コンストラクタ
	 * @param barcodeText バーコードテキスト
	 * @throws BarcodeReaderException 各種情報が取得できません
	 */
	public BarcordReader(String barcodeText)  throws BarcodeReaderException {
		this.barcodeText = barcodeText;
		setBarcodeLine();
		setBarcodeType();
		setBarcodeLine();
		setPartsCode();
		setPcs();
		setSerial();
		setMakerCode();
	}

	/**
	 * バーコードの読み込み行を返します
	 * @return barcodeLine バーコードの読み込み行
	 */
	public int getBarcodeLine() {
		return barcodeLine;
	}

	/**
	 * 入力したバーコードテキストを返します
	 * @return barcodeText　バーコードテキスト
	 */
	public String getBarcodeText() {
		return barcodeText;
	}

	/**
	 * バーコードのタイプを返します<br>
	 * （0＝通常品、1＝オラクル版、3＝社外版）
	 * @return barcodeType　バーコードのタイプ
	 */
	public int getBarcodeType() {
		return barcodeType;
	}

	/**
	 * メーカーコードを返します
	 * @return makerCode　メーカーコード
	 * @throws BarcodeReaderException メーカーコードを読み込めません
	 */
	public String getMakerCode() throws BarcodeReaderException {
		if(makerCode == null) {
			throw new BarcodeReaderException("メーカーコードを読み込めません");
		}
		return makerCode;
	}

	/**
	 * 部品コードを返します
	 * @return partsCode　部品コード
	 * @throws BarcodeReaderException 部品コードを読み込めません
	 */
	public String getPartsCode() throws BarcodeReaderException {
		if(partsCode == null) {
			throw new BarcodeReaderException("部品コードを読み込めません");
		}
		return partsCode;
	}

	/**
	 * 部品の個数を返します
	 * @return pcs　部品の個数
	 * @throws BarcodeReaderException 個数データを読み込めません
	 */
	public int getPcs() throws BarcodeReaderException {
		if(pcs == 0) {
			throw new BarcodeReaderException("個数データを読み込めません");
		}
		return pcs;
	}

	/**
	 * シリアルを返します
	 * @return serial　シリアル
	 * @throws BarcodeReaderException シリアルを読み込めません
	 */
	public String getSerial() throws BarcodeReaderException {
		if(serial == null) {
			throw new BarcodeReaderException("シリアルを読み込めません");
		}
		return serial;
	}

	/**
	 * バーコードの読み取り行を調べます<br>
	 * （0＝不明、1＝１行目、2＝２行目）
	 * @param barcodeLine セットする barcodeLine
	 * @throws BarcodeReaderException 適切なバーコード形式ではありません
	 */
	private void setBarcodeLine() throws BarcodeReaderException {
		boolean firstLine = barcodeText.contains("3N1");
		boolean secondLine = barcodeText.contains("3N2");
		if(firstLine == true) {
			this.barcodeLine = 1;
		}else if(secondLine == true) {
			this.barcodeLine = 2;
		}else {
			throw new BarcodeReaderException("適切なバーコード形式ではありません");
		}
	}

	/**
	 * バーコードデータを更新します
	 * @param barcodeText セットする barcodeText
	 * @throws BarcodeReaderException
	 */
	public void setBarcodeText(String barcodeText) throws BarcodeReaderException {
		this.barcodeText = barcodeText;
		setBarcodeLine();
		setBarcodeType();
		setBarcodeLine();
		setPartsCode();
		setPcs();
		setSerial();
		setMakerCode();
	}

	/**
	 * バーコードのタイプを識別します<br>
	 * （0＝通常品、1＝オラクル版、3＝社外版）
	 * @param barcodeType セットする barcodeType
	 */
	private void setBarcodeType() {
		boolean spaceCheck = barcodeText.contains(" ");
		int spaceIndex = barcodeText.indexOf(" ");
		switch(barcodeLine) {
		case 1:
			if(spaceCheck == true && spaceIndex != -1) {
				int barcodeTextLength = barcodeText.length();
				int spacePoint = barcodeText.indexOf(" ");
				if(barcodeTextLength > ++spacePoint) {
					this.barcodeType = 0;
				}else {
					this.barcodeType = 1;
				}
			}else {
				this.barcodeType = 2;
			}
			break;
		case 2:
			if(spaceIndex == 3) {
				this.barcodeType = 0;
			}else {
				this.barcodeType = 1;
			}
		}
	}

	/**
	 * メーカーコードを識別します
	 * @param makerCode セットする makerCode
	 */
	private void setMakerCode() {
		int barcodeLenght = barcodeText.length();
		int endSpacePoint = barcodeText.lastIndexOf(" ");
		if(barcodeLine == 2 && endSpacePoint != -1) {
			this.makerCode = barcodeText.substring(endSpacePoint, barcodeLenght);
		}
	}

	/**
	 * 部品コードを取得します
	 * @param partsCode セットする partsCode
	 */
	private void setPartsCode() {
		switch(barcodeType) {
		case 0:
			if(barcodeLine == 1 && barcodeText.length() > 3) {
				int spacePoint = barcodeText.indexOf(" ");
				this.partsCode = barcodeText.substring(3, spacePoint);
			}
			break;
		case 1:
			if(barcodeLine == 1 && barcodeText.length() > 3) {
				int spacePoint = barcodeText.indexOf(" ");
				this.partsCode = barcodeText.substring(3, spacePoint);
			}
			break;
		case 2:
			if(barcodeLine == 1 && barcodeText.length() > 3) {
				int barcodeLenght = barcodeText.length();
				this.partsCode = barcodeText.substring(3, barcodeLenght);
			}
		}
	}

	/**
	 * 部品の個数を取得します
	 * @param pcs セットする pcs
	 */
	private void setPcs() {
		int barcodeLenght = barcodeText.length();
		switch(barcodeType) {
		case 0:
			if(barcodeLine == 1) {
				int spacePoint = barcodeText.indexOf(" ");
				this.pcs = Integer.valueOf(barcodeText.substring(spacePoint + 1, barcodeLenght));
			}
			break;
		case 1:
			if(barcodeLine == 2) {
				int spacePoint = barcodeText.indexOf(" ");
				this.pcs = Integer.valueOf(barcodeText.substring(3, spacePoint));
			}
			break;
		case 2:
			if(barcodeLine == 2) {
				int spacePoint = barcodeText.indexOf(" ");
				this.pcs = Integer.valueOf(barcodeText.substring(3, spacePoint));
			}
		}
	}

	/**
	 * シリアルを取得します
	 * @param serial セットする serial
	 */
	private void setSerial() {
		if(barcodeLine == 2) {
			int firstSpacePoint = barcodeText.indexOf(" ");
			int endSpacePoint = barcodeText.lastIndexOf(" ");
			this.serial = barcodeText.substring(firstSpacePoint, endSpacePoint);
		}
	}


}
