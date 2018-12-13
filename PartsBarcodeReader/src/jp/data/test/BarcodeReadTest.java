package jp.data.test;

import java.util.Scanner;

import jp.data.exception.BarcodeReaderException;
import jp.data.model.BarcordReader;

public class BarcodeReadTest {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("バーコード入力してください");
		String barcodeText = sc.nextLine();
		BarcordReader br = new BarcordReader();
		try {
			br.setBarcodeText(barcodeText);
			System.out.println("部品コード：" + br.getPartsCode());
			System.out.println("個数：" + br.getPcs());
			System.out.println("シリアル：" + br.getSerial());
			System.out.println("メーカーコード：" + br.getMakerCode());
		} catch (BarcodeReaderException e) {
			System.out.println(e.getMessage());
		}
	}

}
