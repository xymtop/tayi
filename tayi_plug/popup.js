// popup.js
document.getElementById('searchButton').addEventListener('click', function() {
    var hashValue = document.getElementById('hashInput').value;
    // 添加与区块链交互的代码
    // 例如，使用fetch API获取数据
    fetch('https://your-blockchain-api.com/data?hash=' + hashValue)
        .then(response => response.json())
        .then(data => {
            document.getElementById('result').textContent = JSON.stringify(data);
        })
        .catch(error => console.error('Error:', error));
});
