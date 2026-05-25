CXX = g++
CXXFLAGS = -Wall -O2
TARGET = LAB2_Apellido1_Apellido2
SRC = LAB2_Apellido1_Apellido2.cpp

all: $(TARGET)

$(TARGET): $(SRC)
	$(CXX) $(CXXFLAGS) -o $(TARGET) $(SRC)

clean:
	rm -f $(TARGET) *.o salidaFork.txt