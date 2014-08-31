module Lab4QuineMcCluskey(
input a,
input b,
input c,
input d,
input e,
input f,
input g,
input h,
input i,
input j,
input k,
output out
);
	assign out = ((~a)&b&(~d)&(~f)&(~h)&(~i)&(~j)&(~k))|((~a)&(~b)&c&d&(~f)&(~h)&(~i)&(~j)&(~k))|((~a)&(~b)&(~d)&(~e)&f&(~g)&(~h)&(~i)&(~j)&(~k))|(a&b&c&(~d)&f&(~g)&(~h)&(~i)&(~j)&(~k))|((~a)&b&c&d&(~e)&f&(~h)&(~i)&(~j)&(~k))|(a&(~b)&(~d)&e&f&(~h)&(~i)&(~j)&(~k))|((~a)&(~c)&d&e&(~g)&(~h)&(~i)&(~j)&(~k))|(a&(~c)&(~d)&f&g&(~h)&(~i)&(~j)&(~k))|(a&b&c&d&e&g&(~h)&(~i)&(~j)&(~k))|(a&(~c)&(~d)&(~e)&(~f)&(~h)&(~i)&(~j)&(~k))|((~a)&b&(~c)&(~e)&(~f)&(~h)&(~i)&(~j)&(~k))|((~a)&(~b)&(~c)&e&(~f)&(~h)&(~i)&(~j)&(~k))|(a&(~b)&c&(~d)&(~f)&(~g)&(~h)&(~i)&(~j)&(~k))|(a&b&(~c)&d&f&(~g)&(~h)&(~i)&(~j)&(~k))|(c&d&(~e)&(~f)&g&(~h)&(~i)&(~j)&(~k))|((~a)&c&e&(~f)&g&(~h)&(~i)&(~j)&(~k))|((~a)&b&(~e)&g&(~h)&(~i)&(~j)&(~k))|((~a)&d&(~e)&f&g&(~h)&(~i)&(~j)&(~k))|(a&(~b)&(~c)&d&(~e)&(~h)&(~i)&(~j)&(~k))|((~a)&b&c&(~d)&e&(~h)&(~i)&(~j)&(~k))|(a&b&c&e&(~f)&(~g)&(~h)&(~i)&(~j)&(~k))|(a&(~c)&d&e&(~f)&(~h)&(~i)&(~j)&(~k));
endmodule
