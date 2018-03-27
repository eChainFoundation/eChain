pragma solidity ^0.4.4;

contract Save {
    string[] sign;
    address public owner;
    event SetString(uint key,string types);
    function Save(){
        owner = msg.sender;
    }
    function setstring(string md5) public returns(uint,string){
        var key = sign.push(md5);
        if(key<0) 
            SetString(0,'error');
        else
            SetString(key-1,'susser');
    }
    
    function getString(uint key) public view returns(string){
        return sign[key];
    }
    
}
