export GPG_TTY=$(tty)     
rm -rf target/
mvn clean deploy
