default: BlackJack.java
	javac BlackJack.java

run: BlackJack.class
	java BlackJack

clean:
	rm -f *.class
