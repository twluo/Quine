module Lab4Minimized(
input a,
input b,
input c,
output out
);
	assign out = ((~b)&(~c))|((~a)&b)|(a&c);
endmodule
