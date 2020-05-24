# JavaComputerCodingGame
 This is a game in the same vein as Exapunks, Shenzhen I/O and TIS-100. Right now I am still developing it and it is not feature complete. Though it does run.
 There is one puzzle available - it is easy to beat. Before long I hope to have implemented at least 5 more. They are each tied to specific gameplay features.
 
## Programming Language
The game features a low-level programming language similar to the one found in Exapunks and it has characteristics of Assembly. Below is an overview of the current language keyword implemented.
 
### Basic instructions
- [ ] MOV [R/I R]: copy content. This can be to either a register, CPU (implicit) or to main memory (file)
- [ ] NOOP: do nothing for one cycle. Can be used to keep scripts in sync
- [ ] MARK [value] statement: destination for the jump ‘JMP’ statement
- [ ] JMP [value]: jump to a particular line marked with ‘MARK’
- [ ] BREAK: move to the first line beyond the next ‘JMP’ statement
- [ ] OUT [R/I]: copies value from register to output
 
### Test instruction
- [ ] TEST [R/I operand R/I]: evaluates to a Boolean value which is placed in ‘BooleanRegister’. The test instruction can compare integer values as well as keywords, but not at the same time. As such comparison between integer and keyword is always false. The Test instruction has several operands.
	- [ ] = equal to
	- [ ] > greater than
	- [ ] < less than

### Arithmetic 
- [ ] ADD [R/I R/I R]: addition. The value of the first operand is added to the second and perhaps stored in the third which is always a register.
- [ ] SUB [R/I R/I R]: subtract. Same as ADD but then subtracts values.
- [ ] MUL and DIV [R/I R/I R]: multiply. Get the multiplication of two numbers. The value is stored in the third register operand.
- [ ] DIV [R/I R/I R]: divisor. Same as power but now we divide by the second operand.
- [ ] REM [R/I R/I R]: remainder. Same as power, but now we seek the remainder.
- [ ] SWP [R R]: swap values. The value of the first register is swapped with the second.
 
Operations on files and RAM are being worked on and basic network coding will follow afterwards. If you have questions or if you want to contribute feel free to email me.