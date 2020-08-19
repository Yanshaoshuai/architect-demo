package com.yan.architect.architectdemo.common.utils;

import com.marcopolo.marcopoloplatform.config.AppProperties;
import com.marcopolo.marcopoloplatform.model.Token;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.tweb3j.crypto.*;
import org.tweb3j.utils.Convert;
import org.tweb3j.utils.Numeric;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import party.loveit.bip44forjava.utils.Bip44Utils;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Web3jUtils {
    public static Web3j web3j = Web3j.build(new HttpService(AppProperties.WEB3J_ADDRESS));

    public static void main(String[] args) throws Exception {
        //System.out.println(getBalance("https://rpc.truescan.network/testnet","0xa596bda0ea79c9478ab0071fc91d1fb02a3a57e8"));
        /*System.out.println(getBalanceERC20(
            "https://rpc.truescan.network/testnet",
            "TRUE",
            "0xdb21abddd12a51caf155c16c5ed075c9f2183b9f",
            "0x189cF5e2079686ff909EfdA350D5c66d28B6857a"));*/

        System.out.println(checkTransIsSuccess("0xf4454596b2949c29674f4ceaacb41c0101b9d810531156f19fe6113cb935123e"));
    }

    //披露查询token余额
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void queryBalanceByAddr2(String addressStr, List<Address> cnAddrStrs) {
        try {
            String fromAddress = "0x0D4ab67E7C03556353a00FAc85D6F68e12C0b7e7";
            List<Type> inputParameters = new ArrayList<>();

            Address address = new Address(addressStr);
            DynamicArray<Address> contractAddresss = new DynamicArray<Address>(cnAddrStrs);

            inputParameters.add(address);
            inputParameters.add(contractAddresss);

            List<TypeReference<?>> outputParameters = new ArrayList<>();
            TypeReference<DynamicArray<Uint256>> balances = new TypeReference<DynamicArray<Uint256>>() {
            };
            outputParameters.add(balances);

            Function function = new Function("getBalances", inputParameters, outputParameters);
            String encodedFunction = FunctionEncoder.encode(function);

            Transaction transaction = Transaction.createEthCallTransaction(
                    fromAddress, "0x87240498a639f114aa3d2f94ac2a826b8ad81316", encodedFunction);

            EthCall ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.PENDING).send();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            List<Uint256> balancesList = (List<Uint256>) results.get(0).getValue();

            for (int i = 0; i < balancesList.size(); i++) {
                System.out.println(new BigDecimal(balancesList.get(i).getValue().toString()).divide(new BigDecimal("1000000000000000000"), 6, BigDecimal.ROUND_DOWN));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //披露查询token余额
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<Token> queryBalanceByAddr(String chain, String addressStr, List<Token> tokenRes) {
        try {
            //管理员地址
            String fromAddress = "";
            //合约地址
            String toAddress = "";
            if ("TRUE".equals(chain)) {
                fromAddress = "0xe1E7fef1dC9602DcFd5Fd798C876466167F0eb25";
                toAddress = "0xA9e4fE401200CC6DFd36f91aDc801Aa57Dbfba5B";
            } else {
                web3j = Web3j.build(new HttpService(AppProperties.ETH_WEB3J_ADDRESS));
                fromAddress = "0x0D4ab67E7C03556353a00FAc85D6F68e12C0b7e7";
                toAddress = "0x87240498a639f114aa3d2f94ac2a826b8ad81316";
            }

            List<Address> cnAddrStrs = new ArrayList<Address>();
            for (Token token : tokenRes) {
                if ("TRUE".equals(token.getName()) || "ETH".equals(token.getName())) {
                    cnAddrStrs.add(new Address("0xfFf0000000000000000000000000000000000000"));
                } else {
                    cnAddrStrs.add(new Address(token.getContractAddr()));
                }
            }

            List<Type> inputParameters = new ArrayList<>();

            Address address = new Address(addressStr);
            DynamicArray<Address> contractAddresss = new DynamicArray<Address>(cnAddrStrs);

            inputParameters.add(address);
            inputParameters.add(contractAddresss);

            List<TypeReference<?>> outputParameters = new ArrayList<>();
            TypeReference<DynamicArray<Uint256>> balances = new TypeReference<DynamicArray<Uint256>>() {
            };
            outputParameters.add(balances);

            Function function = new Function("getBalances", inputParameters, outputParameters);
            String encodedFunction = FunctionEncoder.encode(function);

            Transaction transaction = Transaction.createEthCallTransaction(
                    fromAddress, toAddress, encodedFunction);

            EthCall ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.PENDING).send();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            List<Uint256> balancesList = (List<Uint256>) results.get(0).getValue();

            for (int i = 0; i < tokenRes.size(); i++) {
                Token token = tokenRes.get(i);
                token.setTokenBalance(new BigDecimal(balancesList.get(i).getValue().toString()).divide(new BigDecimal(token.getDecimalPoint()), 6, BigDecimal.ROUND_DOWN));
            }

            return tokenRes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //代币转账（代付）
    @SuppressWarnings("rawtypes")
    public static String sendTxByERC20WithCredentials(String toAddress, Credentials credentials,
                                                      String amount, Credentials credentials_payment) throws InterruptedException, ExecutionException, IOException, CipherException {
        String fromAddress = credentials.getAddress();

        org.web3j.protocol.core.methods.response.EthGetTransactionCount ethGetTransactionCount = web3j
                .ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        BigDecimal bi1 = new BigDecimal(amount);
        BigDecimal bi2 = new BigDecimal("1000000000000000000");
        BigDecimal bi3 = bi1.multiply(bi2);

        Function function = new Function("transfer",
                Arrays.asList(new Address(toAddress), new Uint256(new BigInteger(StringUtils.substringBefore(bi3.toString(), ".")))),
                Arrays.asList(new TypeReference<Type>() {
                }));
        String encodedFunction = FunctionEncoder.encode(function);

        TrueRawTransaction trueRawTransaction = TrueRawTransaction.createTransaction(
                nonce,
                Convert.toWei("1", Convert.Unit.GWEI).toBigInteger(),
                Convert.toWei("50000", Convert.Unit.WEI).toBigInteger(),
                "0x".concat(AppProperties.TOKEN_CONTRACT_ADDRESS),
                new BigInteger("0"),
                encodedFunction,
                null,
                "0x".concat(AppProperties.DF_ADMIN_ADDRESS));

        byte[] signedMessage = TrueTransactionEncoder.signMessage_payment(
                trueRawTransaction, AppProperties.CHAIN_ID, credentials, credentials_payment);
        String hexValue = Numeric.toHexString(signedMessage);

        org.web3j.protocol.core.methods.response.EthSendTrueTransaction ethSendTrueTransaction =
                web3j.ethSendTrueRawTransaction(hexValue).send();
        String transactionHash = ethSendTrueTransaction.getTransactionHash();

        return transactionHash;
    }

    //生成钱包地址
    public static Map<String, String> createWalletAddressByWords() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        List<String> words = Bip44Utils.generateMnemonicWords();
        BigInteger prieth = Bip44Utils.getPathPrivateKey(words, "m/44'/60'/0'/0/0");
        ECKeyPair ecKeyPair = ECKeyPair.create(prieth);
        String address = "0x" + Keys.getAddress(ecKeyPair);

        map.put("words", words.toString());
        map.put("address", address);
        return map;
    }

    /*
     * 获取代币余额
     * url 节点地址
     * chain 所属链
     * accountAddress 账户地址
     * contractAddress 代币合约地址
     */
    @SuppressWarnings("rawtypes")
    public static String getBalanceERC20(String url, String chain, String accountAddress,
                                         String contractAddress, String tokenType) {
        String methodName = "balanceOf";
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Address address = new Address(accountAddress);
        inputParameters.add(address);

        TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(accountAddress, contractAddress, data);

        EthCall ethCall;
        BigInteger balanceValue = BigInteger.ZERO;

        try {
            Web3j web3j = Web3j.build(new HttpService(url));
            ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());

            // 多18个0 ，需处理
            balanceValue = results.size() > 0 ? (BigInteger) results.get(0).getValue() : BigInteger.ZERO;
            if (("TRUE".equals(chain) && !"T-USDT".equals(tokenType))
                    || ("ETH".equalsIgnoreCase(chain) && "0x49229C3902d49BE6443E01C0251b02780397ab1A".equalsIgnoreCase(contractAddress))) {
                BigDecimal banlance = Convert.fromWei(balanceValue.toString(), Convert.Unit.ETHER);
                BigDecimal setScale = banlance.setScale(8, BigDecimal.ROUND_DOWN);

                return setScale.toPlainString();
            } else {
                BigDecimal balanceValueBD = new BigDecimal(balanceValue);
                BigDecimal balance = balanceValueBD.divide(new BigDecimal("1000000"), 6, BigDecimal.ROUND_DOWN);

                return balance.toPlainString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "0";
        }
    }

    public static String getBalanceERC20(String url, String chain, String accountAddress,
                                         String contractAddress, String tokenType, String point) {
        String methodName = "balanceOf";
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Address address = new Address(accountAddress);
        inputParameters.add(address);

        TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(accountAddress, contractAddress, data);

        EthCall ethCall;
        BigInteger balanceValue = BigInteger.ZERO;

        try {
            Web3j web3j = Web3j.build(new HttpService(url));
            ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());

            // 多18个0 ，需处理
            balanceValue = results.size() > 0 ? (BigInteger) results.get(0).getValue() : BigInteger.ZERO;
            BigDecimal balance = new BigDecimal(balanceValue).divide(new BigDecimal(point), 6, BigDecimal.ROUND_DOWN);
            return balance.toPlainString();
        } catch (IOException e) {
            e.printStackTrace();
            return "0";
        }
    }


    /*
     * 获取主币余额
     * url 节点地址
     * address 账户地址
     */
    public static String getBalance(String url, String address) throws IOException {
        Web3j web3j = Web3j.build(new HttpService(url));
        EthGetBalance ethGetBalance1 = web3j.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();

        BigDecimal balance = new BigDecimal(ethGetBalance1.getBalance().divide(new BigInteger("10000000000000")).toString());
        BigDecimal balanceBD = balance.divide(new BigDecimal("100000"), 8, BigDecimal.ROUND_DOWN);
        return balanceBD.toPlainString();
    }

    //获取交易状态
    public static String getTransStatus(String transHash, int type) throws IOException {
        Web3j web3jEth = null;
        if (type == 1) {
            web3jEth = Web3j.build(new HttpService("https://ethrpc.truewallet.net"));
        } else {
            web3jEth = Web3j.build(new HttpService("https://rpc.truechain.network"));
        }

        EthGetTransactionReceipt transactionReceipt = web3jEth.ethGetTransactionReceipt(transHash).send();
        TransactionReceipt receipt = transactionReceipt.getResult();

        String status = "0";
        if (null != receipt) {
            if ("0x1".equals(receipt.getStatus())) {
                status = "1";
            } else {
                status = "2";
            }
        }

        return status;
    }

    //获取交易手续费
    public static String getGasUsed(String transHash, String gasPrice) throws IOException {
        Web3j web3jEth = Web3j.build(new HttpService("https://ethrpc.truewallet.net"));

        EthGetTransactionReceipt transactionReceipt = web3jEth.ethGetTransactionReceipt(transHash).send();
        TransactionReceipt receipt = transactionReceipt.getResult();

        String gasUsedStr = (null != receipt && null != receipt.getGasUsed()) ? receipt.getGasUsed().toString() : "0";

        BigDecimal gasUsed = new BigDecimal(gasUsedStr);
        BigDecimal n18 = new BigDecimal("1000000000000000000");
        BigDecimal gasPriceBd = new BigDecimal(gasPrice);

        BigDecimal usedGas = gasUsed.multiply(gasPriceBd).divide(n18);
        return usedGas.stripTrailingZeros().toPlainString();
    }

    //支付确认
    @SuppressWarnings("rawtypes")
    public static String transferSet(
            String coinAddrStr, String toStr,
            String toValueStr, String nationalTaxStr, String localTaxStr, String gasTaxStr,
            String privateKey, boolean isHostCoinStr) throws UnsupportedEncodingException {
        try {
            Credentials credentials = Credentials.create(privateKey);
            String fromAddress = credentials.getAddress();

            EthGetTransactionCount ethGetTransactionCount = web3j
                    .ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();

            Address coinAddr = new Address(coinAddrStr);
            Address to_ = new Address(toStr);

            Uint256 toValue = new Uint256(new BigInteger(toValueStr));
            Uint256 nationalTax = new Uint256(new BigInteger(nationalTaxStr));
            Uint256 localTax = new Uint256(new BigInteger(localTaxStr));

            Uint256 gasTax = new Uint256(new BigInteger(gasTaxStr));
            Bool isHostCoin = new Bool(isHostCoinStr);

            List<Type> inputParameters = new ArrayList<>();

            inputParameters.add(coinAddr);
            inputParameters.add(to_);
            inputParameters.add(toValue);

            inputParameters.add(nationalTax);
            inputParameters.add(localTax);
            inputParameters.add(gasTax);
            inputParameters.add(isHostCoin);

            List<TypeReference<?>> outputParameters = new ArrayList<>();
            TypeReference<Bool> result = new TypeReference<Bool>() {
            };
            outputParameters.add(result);

            Function function = new Function("transferSet", inputParameters, outputParameters);
            String encodedFunction = FunctionEncoder.encode(function);

            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce,
                    Convert.toWei("1", Convert.Unit.GWEI).toBigInteger(),
                    new BigInteger("3000000"), "0x".concat(AppProperties.PRIN_POOL_CONTRACT_ADDRESS), encodedFunction);

            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, AppProperties.CHAIN_ID, credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
            return ethSendTransaction.getTransactionHash();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "err";
        } catch (ExecutionException e) {
            e.printStackTrace();
            return "err";
        }
    }

    //根据keystore解锁账号返回私钥
    public static String getPrivatekeybyKeystore(String keyStorePass, String type) {
        try {
            ClassPathResource resource = null;

            if ("prod".equals(AppProperties.SYSTEM_ENVIRONMENT)) {
                if ("df".equals(type)) {
                    resource = new ClassPathResource("keystore_prod_df.json");
                } else if ("hy".equals(type)) {
                    resource = new ClassPathResource("keystore_prod_hy.json");
                } else {
                    resource = new ClassPathResource("keystore_prod_ex.json");
                }
            } else {
                if ("df".equals(type)) {
                    resource = new ClassPathResource("keystore_test_df.json");
                } else if ("hy".equals(type)) {
                    resource = new ClassPathResource("keystore_test_hy.json");
                } else {
                    resource = new ClassPathResource("keystore_test_ex.json");
                }
            }

            File files = asFile(resource.getInputStream(), type);

            org.web3j.crypto.Credentials credentials = WalletUtils.loadCredentials(keyStorePass, files);
            String privatekey = credentials.getEcKeyPair().getPrivateKey().toString(16);

            if (files.exists() && files.isFile()) {
                files.delete();
            }

            return privatekey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File asFile(InputStream inputStream, String type) throws IOException {
        File tmp = null;

        if ("prod".equals(AppProperties.SYSTEM_ENVIRONMENT)) {
            if ("df".equals(type)) {
                tmp = File.createTempFile("keystore_prod_df", ".json", new File("/"));
            } else if ("hy".equals(type)) {
                tmp = File.createTempFile("keystore_prod_hy", ".json", new File("/"));
            } else {
                tmp = File.createTempFile("keystore_prod_ex", ".json", new File("/"));
            }
        } else {
            if ("df".equals(type)) {
                tmp = File.createTempFile("keystore_test_df", ".json", new File("/"));
            } else if ("hy".equals(type)) {
                tmp = File.createTempFile("keystore_test_hy", ".json", new File("/"));
            } else {
                tmp = File.createTempFile("keystore_test_ex", ".json", new File("/"));
            }
        }

        OutputStream os = new FileOutputStream(tmp);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        return tmp;
    }


    /**
     * 查交易信息
     * <p>
     * private String hash;
     * private String nonce;
     * private String blockHash;
     * private String blockNumber;
     * private String transactionIndex;
     * private String from;
     * private String to;
     * private String value;
     * private String gasPrice;
     * private String gas;
     * private String input;
     * private String creates;
     * private String publicKey;
     * private String raw;
     * private String r;
     * private String s;
     * private int v;
     *
     * @param transHash
     * @return
     */
    public static org.web3j.protocol.core.methods.response.Transaction queryTransactionByHash(String transHash) {
        try {
            Web3j web3j = Web3j.build(new HttpService("https://rpc.truechain.network"));
            EthTransaction transaction = web3j.ethGetTransactionByHash(transHash).send();

            return transaction.getResult();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查交易快照 gasUsed status等
     * <p>
     * private String transactionHash;
     * private String transactionIndex;
     * private String blockHash;
     * private String blockNumber;
     * private String cumulativeGasUsed;
     * private String gasUsed;
     * private String contractAddress;
     * private String root;
     * private String status;
     * private String from;
     * private String to;
     * private List<Log> logs;
     * private String logsBloom;
     *
     * @param transHash
     * @return
     */
    public static org.web3j.protocol.core.methods.response.TransactionReceipt queryEthGetTransactionReceiptByHash(String transHash) {
        try {
            EthGetTransactionReceipt transactionReceipt = web3j.ethGetTransactionReceipt(transHash).send();
            return transactionReceipt.getResult();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断交易是否执行成功
     *
     * @param txHash   交易hash
     * @return 1 表示执行成功
     * 2 表示执行失败
     * 0 表示还未执行
     */
    public static String checkTransIsSuccess(String txHash) {
        //确认交易已执行
        EthGetTransactionReceipt receipt = null;
        int queryTimes = 0;
        int totalTimes = 100;

        try {
            while (queryTimes < totalTimes) {
                receipt = web3j.ethGetTransactionReceipt(txHash).send();
                if (receipt.getResult() != null
                        && StringUtils.isNotBlank(receipt.getResult().getStatus())) {
                    String txStatus = receipt.getResult().getStatus();
                    System.out.println("txHash:" + txHash + "  status===>" + txStatus);
                    switch (txStatus) {
                        case "0x1":
                            return "1";
                        case "0x0":
                            if (receipt.getError() != null) {
                                System.out.println("txHash=" + txHash + "  receipt error = " + receipt.getError().getMessage());
                            }
                            return "2";
                        default:
                            System.out.println("unkown txStatus=" + txStatus);
                            return "2";
                    }
                }
                queryTimes++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }
}

