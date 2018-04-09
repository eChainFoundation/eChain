pragma solidity ^0.4.18;
  
import "./StandardToken.sol";

contract ECtoken is StandardToken {
  string public name = "ECtoken";
  string public symbol = "ECT";
  uint8 public decimals = 18;
  uint256 public INITIAL_SUPPLY = 2000000000e18;
  function ECtoken() public {
    totalSupply_ = INITIAL_SUPPLY;
    balances[msg.sender] = INITIAL_SUPPLY;
  }
}
