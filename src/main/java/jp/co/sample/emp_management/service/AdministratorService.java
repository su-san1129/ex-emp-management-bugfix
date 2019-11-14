package jp.co.sample.emp_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.emp_management.domain.Administrator;
import jp.co.sample.emp_management.repository.AdministratorRepository;

/**
 * 管理者情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class AdministratorService {
	
	@Autowired
	private AdministratorRepository administratorRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 管理者情報を登録します.
	 * 
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		administrator.setPassword( passwordEncoder.encode(administrator.getPassword()) );
		administratorRepository.insert(administrator);
	}
	
	/**
	 * ログインをします.
	 * @param mailAddress メールアドレス
	 * @param password パスワード
	 * @return 管理者情報 存在しない場合はnullが返ります
	 */
	public Administrator login(String mailAddress, String passward) {
		Administrator administrator = administratorRepository.findByMailAddressAndPassward(mailAddress, passward);
		return administrator;
	}
	
	/**
	 * メールアドレスの一致する管理者情報を取得します.
	 * 
	 * @param mailAddress 検索したいメールアドレス
	 * @return 検索された管理者
	 */
	public Administrator findByMailAddress(String mailAddress) {
		Administrator administrator = administratorRepository.findByMailAddress(mailAddress);
		return administrator;
		
	}
	
	/**
	 * パスワードが入力された内容と等しいかチェックします.
	 * 
	 * @param password パスワード
	 * @param passwordConfirm 確認用パスワード
	 * @return 比較結果を返します。比較が正しければ、true、違っていればfalseを返す。
	 */
	public boolean isCheckPassword(String password, String passwordConfirm) {
		return passwordConfirm.equals(password);
	}
	
	/**
	 * メールアドレスの一致するユーザを取得し、パスワードが正しいかどうか比較する.
	 * 
	 * @param mailAddress 検索するメールアドレス
	 * @param password フォームから送られたパスワード
	 * @return パスワードチェックが通った管理者。通らないときはnullを返却。
	 */
	public Administrator loginByCheckAdministrator(String mailAddress, String password) {
		Administrator checkAdministrator = administratorRepository.findByMailAddress(mailAddress);
		if( checkAdministrator == null ) {
			return null;
		}
		if( passwordEncoder.matches( password, checkAdministrator.getPassword()) ) {
			return checkAdministrator;
		}
		return null;
	}
}
