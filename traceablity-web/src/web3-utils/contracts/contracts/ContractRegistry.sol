// 定义MIT许可证
// SPDX-License-Identifier: MIT


pragma solidity ^0.8.20;

//证明监察中心
// 定义一个名为ContractRegistry的智能合约
contract ContractRegistry {
    // 定义一个结构体来存储关于某个合约的信息
    struct ContractInfo {
        address contractAddress; // 合约的地址
        string name; // 合约的名字
        string description; // 合约的描述
        address owner; // 提交合约的用户地址
        uint256 applicationTime; // 提交合约的时间
        string[] whitepaperIPFSHashes; // 白皮书的IPFS哈希数组
        Status status; // 合约的状态
    }

   
   
    // 定义一个枚举类型，表示合约的不同状态
    enum Status {
        Pending, // 待审核
        Approved, // 已通过审核
        Rejected, // 被拒绝
        Offline // 下线
    }

    // 使用映射存储每个地址对应的合约信息
    mapping(address => ContractInfo) private  contracts;
    // 存储所有合约地址的数组
    address[] private  allContracts;
    // 存储已通过审核的合约地址的数组
    address[] private  approvedContracts;

    //储存用户与合约的数据关系
    mapping (address => mapping (address=>bool) ) private  tracDatas;
    //数据
    mapping (address => address[] ) private  tracAddDatas;

    //新增新的数据
    function addTrac(address _user,address _contract)public {
    

        //判断合约的状态
        // require(isContractApprovedAndNotOffline(_contract),"Contract blocked");
        
        //添加对应信息
        if (tracDatas[_user][_contract]==false){
            tracAddDatas[_user].push(_contract);
            tracDatas[_user][_contract]==true;
        }
    }


   //返回一个用户的所有合约数组
   function getAllContractByUser(address _user) public view  returns(address[] memory) {
      return tracAddDatas[_user];
   }

    // 一个公共函数，用于注册新合约
    function registerContract(
        address _contractAddress, // 合约地址
        string memory _name, // 合约名字
        string memory _description, // 合约描述
        string[] memory _whitepaperIPFSHashes // 白皮书IPFS哈希数组
    ) public  {
        
        //如果包含了这个合约，就不能重复创建
        require(contracts[_contractAddress].contractAddress == address(0), "Contract  registered"); // 确保合约没被注册
        ContractInfo storage cInfo = contracts[_contractAddress]; // 获取或创建合约信息
        cInfo.contractAddress = _contractAddress; // 设置合约地址
        cInfo.name = _name; // 设置合约名字
        cInfo.description = _description; // 设置合约描述
        cInfo.owner = msg.sender; // 设置合约所有者为消息发送者
        cInfo.applicationTime = block.timestamp; // 设置申请时间为当前区块时间
        cInfo.whitepaperIPFSHashes = _whitepaperIPFSHashes; // 设置白皮书IPFS哈希
        cInfo.status = Status.Pending; // 将合约状态设置为待审核
        allContracts.push(_contractAddress); // 将合约地址添加到所有合约列表中
    }

    // 一个公共函数，用于更新合约的状态
    function updateStatus(
        address _contractAddress, // 合约地址
        Status _status // 新状态
    ) public  {
        require(contracts[_contractAddress].contractAddress != address(0), "Contract not registered"); // 确保合约已被注册
        contracts[_contractAddress].status = _status; // 更新合约的状态
        if (_status == Status.Approved) { // 如果新状态是已通过审核
            approvedContracts.push(_contractAddress); // 将合约地址添加到已通过审核的合约列表中
        }
    }

    // 一个公共视图函数，返回所有合约地址的数组
    function getAllContracts() public view returns (address[] memory) {
        return allContracts;
    }

    // 一个公共视图函数，返回所有已通过审核的合约地址的数组
    function getApprovedContracts() public view returns (address[] memory) {
        return approvedContracts;
    }

    // 新添加的函数，用于判断合约是否已通过审核并且未下线
    function isContractApprovedAndNotOfflineStruct(address _contractAddress) public view returns (ContractInfo memory) {
        ContractInfo memory cInfo = contracts[_contractAddress];
        return (cInfo);
    }


      // 新添加的函数，用于判断合约是否已通过审核并且未下线
    function isContractApprovedAndNotOffline(address _contractAddress) public view returns (bool) {
        ContractInfo memory cInfo = contracts[_contractAddress];
        return (cInfo.status == Status.Approved && cInfo.status != Status.Offline);
    }

    function isContractRegistered (address _contractAddress) public view returns(bool){
        return  contracts[_contractAddress].contractAddress != address(0);
    }

    function getContractInfo(address _contractAddress)public view returns(ContractInfo memory){
        return contracts[_contractAddress];
    }
}
