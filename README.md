# JavaComputerCodingGame
 This is a computer hacking game in the same spirit as [Exapunks](https://store.steampowered.com/app/716490/EXAPUNKS/), [Shenzhen I/O](https://store.steampowered.com/app/504210/SHENZHEN_IO/) and [TIS-100](https://store.steampowered.com/app/370360/TIS100/). 
 Yet, I intend for this game to have unique features that will ste it apart. Besides beating hacking puzzles it will also feature interactive storytelling, hopefully using a basic version of the [Encounter System](https://www.erasmatazz.com/library/fourth-generation-interacti/the-encounter-system/index.html) as developed by Chris Crawford.
 Right now I am still developing it and it is not feature complete. Though it does run.
 There is one puzzle available - it is easy to beat. Before long I hope to have implemented at least 5 more. The first set of puzzles is each tied to a specific gameplay feature.
 
## Programming Language
The game features a low-level programming language similar to the one found in Exapunks and it has characteristics of Assembly. Below is an overview of the current language keywords implemented.
 
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

### Arithmetic
- [ ] FETCH [R]: fetch a file. If the argument give matches a file a handler to that file will be stored in variable X. If no file by such a name exists a new file will be created.
- [ ] SEEK [I]: move data location handler by a certain value. Can be both positive and negative values
- [ ] VOID: remove value. The value associated with that data location handler is removed, thus shortening the amount of data by 1.
- [ ] DROP: drop the file handler.
- [ ] WIPE: wipe the file associated with file handler clean. 
 
Operations on RAM are being worked on and basic network coding will follow afterwards. If you have questions or if you want to contribute feel free to email me.

### 2020 schedule
2020 is slowly coming to a close. Preferably I want to finish an alpha build by year's end, so the following development schedule is envisioned.
- [ ] File operations (September)
- [ ] Dialogue system (October) 
- [ ] GUI settings menu (October)
- [ ] unit tests for all basic classes (October)
- [ ] Network connectivity (November)
- [ ] RAM operations (November)
- [ ] Debug GUI (November)
- [ ] Encounter system for dialogue (December) 
- [ ] Basic sounds (December)
- [ ] Up to 5 new puzzles (December)