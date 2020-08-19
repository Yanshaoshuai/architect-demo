package com.yan.architect.architectdemo.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AppProperties {

	//文件上传地址
    public static String UPLOAD_FILE_PATH;

    //图片上传路径
    public static String IMAGE_PATH;

    //图片保存地址
    public static String IMAGE_URL;
    
    //web3j地址
    public static String WEB3J_ADDRESS;
    
    //合约编译
    public static String SOL_COMPILE;
    
    public static String MYSQL_URL;
    
    public static String MYSQL_USERNAME;
    
    public static String MYSQL_PASSWORD;
    
    //获取环境参数
    public static String SYSTEM_ENVIRONMENT;
    
    //本金合约地址
    public static String PRIN_POOL_CONTRACT_ADDRESS;
    
    //奖励合约地址
    public static String REWARD_POOL_CONTRACT_ADDRESS;
    
    //代币合约地址
    public static String TOKEN_CONTRACT_ADDRESS;
    
    //TRUE合约地址
    public static String TRUE_CONTRACT_ADDRESS;
    
    public static int CHAIN_ID;
    
    public static String TRUESCAN_URL;
    
    public static String RATE_URL;
    
    public static String DF_ADMIN_ADDRESS;
    
    public static String HY_ADMIN_ADDRESS;
    
    public static String TRUESCAN_TX_LOG_URL;
    
    public static String ETHERSACN_URL;
    
    public static String ETHERSACN_TX_LOG_URL;
    
    public static String ETH_WEB3J_ADDRESS;
    
    public static String EMAIL_WHITE_LIST;
    
    public static String BTC_Address;

    public static String IMAGE_CHECK_GEN_URL;

    public static String IMAGE_CHECK_VALIDATE_URL;

    public static String CHAIN;

    public static String SEND_MSG_URL;

    public static String SEND_MSG_PK;

    public static String CONTENT_86;
    @PostConstruct
    public void init() {
        UPLOAD_FILE_PATH = uploadFilePath;
        IMAGE_PATH = imagePath;
        IMAGE_URL = imageUrl;
        
        WEB3J_ADDRESS = web3jAddress;
        SOL_COMPILE = solCompile1;
        
        MYSQL_URL = mysqlUrl;
        MYSQL_USERNAME = mysqlUserName;
        MYSQL_PASSWORD = mysqlPassword;
        
        SYSTEM_ENVIRONMENT = environment;

        PRIN_POOL_CONTRACT_ADDRESS = prinPoolContractAddress;
        REWARD_POOL_CONTRACT_ADDRESS = rewardPoolContractAddress;
        
        TOKEN_CONTRACT_ADDRESS = tokenContractAddress;
        TRUE_CONTRACT_ADDRESS = trueContractAddress;
        
        CHAIN_ID = chainId;
        TRUESCAN_URL = trueScanUrl;
        RATE_URL = rateUrl;
        
        DF_ADMIN_ADDRESS = dfAdminAddress;
        HY_ADMIN_ADDRESS = hyAdminAddress;
        
        TRUESCAN_TX_LOG_URL = truescanTxLogUrl;
        ETHERSACN_URL = ethersacnUrl;
        ETHERSACN_TX_LOG_URL = ethersacnTxLogUrl;
        ETH_WEB3J_ADDRESS = ethWeb3jAddress;
        
        EMAIL_WHITE_LIST = emailWhiteList;
        BTC_Address = btcAddress;
        IMAGE_CHECK_GEN_URL=imageCheckGenUrl;
        IMAGE_CHECK_VALIDATE_URL=imageCheckValidateUrl;
        CHAIN=chain;
        SEND_MSG_URL=sendMsgUrl;
        SEND_MSG_PK=sendMsgPk;
        CONTENT_86=content_86.replace("[$]","$");
    }

    @Value("${app.service.imageCheckGenUrl}")
    private String imageCheckGenUrl;

    @Value("${app.service.imageCheckValidateUrl}")
    private String imageCheckValidateUrl;

    @Value("${app.upload.file-path}")
    private String uploadFilePath;

    @Value("${app.upload.image-path}")
    private String imagePath;

    @Value("${app.upload.image-url}")
    private String imageUrl;

    @Value("${app.web3j.address}")
    private String web3jAddress;
    
    @Value("${app.solCompile}")
    private String solCompile1;
    
    @Value("${app.mysql.url}")
    private String mysqlUrl;
    
    @Value("${app.mysql.username}")
    private String mysqlUserName;
    
    @Value("${app.mysql.password}")
    private String mysqlPassword;
    
    @Value("${spring.profiles.active}")
    private String environment;
    
    @Value("${app.web3j.prinPoolContractAddress}")
    private String prinPoolContractAddress;
    
    @Value("${app.web3j.rewardPoolContractAddress}")
    private String rewardPoolContractAddress;
    
    @Value("${app.web3j.tokenContractAddress}")
    private String tokenContractAddress;
    
    @Value("${app.web3j.trueContractAddress}")
    private String trueContractAddress;
    
    @Value("${app.web3j.chainId}")
    private int chainId;

    @Value("${app.chain.name}")
    private String chain;
    @Value("${app.web3j.truescanUrl}")
    private String trueScanUrl;
    
    @Value("${app.web3j.rateUrl}")
    private String rateUrl;
    
    @Value("${app.web3j.dfAdminAddress}")
    private String dfAdminAddress;
    
    @Value("${app.web3j.hyAdminAddress}")
    private String hyAdminAddress;
    
    @Value("${app.web3j.truescanTxLogUrl}")
    private String truescanTxLogUrl;
    
    @Value("${app.web3j.ethersacnUrl}")
    private String ethersacnUrl;
    
    @Value("${app.web3j.ethersacnTxLogUrl}")
    private String ethersacnTxLogUrl;
    
    @Value("${app.web3j.ethWeb3jAddress}")
    private String ethWeb3jAddress;
    
    @Value("${app.email.whiteList}")
    private String emailWhiteList;
    
    @Value("${app.web3j.btcAddress}")
    private String btcAddress;

    @Value("${app.sms.url}")
    private String sendMsgUrl;
    @Value("${app.sms.publicKey}")
    private String sendMsgPk;
    @Value("${app.sms.content.86}")
    private String content_86;

}
