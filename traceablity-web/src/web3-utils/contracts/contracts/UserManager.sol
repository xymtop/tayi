// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

contract UserManager {
    struct User {
        string nickname;
        bool gender;
        string email;
        string bio;
        string phone;
        bool isBanned;
        string role;
    }

    mapping(address => User) private users;
    address[] private userAddresses;

    // 注册新用户
    function registerUser(
        string memory _nickname,
        bool _gender,
        string memory _email,
        string memory _bio,
        string memory _phone,
        string memory _role
    ) public {
        require(bytes(users[msg.sender].nickname).length == 0, "User already registered.");

        users[msg.sender] = User({
            nickname: _nickname,
            gender: _gender,
            email: _email,
            bio: _bio,
            phone: _phone,
            isBanned: false,
            role: _role
        });

        userAddresses.push(msg.sender);
    }

    //管理员修改用户信息
      function updateUser(
          address _user,
        string memory _nickname,
        bool _gender,
        string memory _email,
        string memory _bio,
        string memory _phone,
        string memory _role,
        bool _isBanned
    ) public {
        users[_user] = User({
            nickname: _nickname,
            gender: _gender,
            email: _email,
            bio: _bio,
            phone: _phone,
            isBanned: _isBanned,
            role: _role
        });
    }
    // 获取用户信息
    function getUserInfo(address _userAddress) public view returns (User memory) {
        require(!users[_userAddress].isBanned, "User is banned.");
        return users[_userAddress];
    }

    // 获取所有用户地址
    function getAllUsers() public view returns (address[] memory) {
        return userAddresses;
    }

    // 禁止用户
    function banUser(address _userAddress) public {
        // 可以添加权限控制，例如只允许合约拥有者执行此操作
        users[_userAddress].isBanned = true;
    }

    // 修改用户角色
    function changeUserRole(address _userAddress, string memory _newRole) public {
        // 可以添加权限控制，例如只允许合约拥有者执行此操作
        users[_userAddress].role = _newRole;
    }

    // 获取用户角色
    function getUserRole(address _userAddress) public view returns (string memory) {
        return users[_userAddress].role;
    }
}
