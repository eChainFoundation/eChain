pragma solidity ^0.4.21;
  
import "./StandardBurnableToken.sol";
import "./Ownable.sol";

contract ECPoints is StandardBurnableToken, Ownable {
  string public name = "ECToints";
  string public symbol = "ECT";
  uint8 public decimals = 0;
  uint256 public INITIAL_SUPPLY = 10000000000000000;

  address public owner;

  function ECPoints() public {
    totalSupply_ = INITIAL_SUPPLY;
    balances[msg.sender] = INITIAL_SUPPLY;
    owner = msg.sender;
  }

  function mintToken(address target, uint256 mintedAmount) public onlyOwner {
    balances[target] += mintedAmount;
    totalSupply_ += mintedAmount;
    Transfer(0, owner, mintedAmount);
    Transfer(owner, target, mintedAmount);
  }

}
