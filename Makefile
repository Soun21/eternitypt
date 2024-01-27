TARGET = eternity
CMP = g++
FLAGS = -Wall
SRC_DIR = sources
INC_DIR = headers
SOURCES = $(wildcard $(SRC_DIR)/*.cpp)
HEADERS = $(wildcard $(INC_DIR)/*.hpp)
OBJECTS = $(SOURCES:$(SRC_DIR)/%.cpp=%.o)

$(TARGET): $(OBJECTS)
	$(CMP) -o $@ $^

%.o: $(SRC_DIR)/%.cpp $(HEADERS)
	$(CMP) $(FLAGS) -I$(INC_DIR) -c $< -o $@

clean:
	rm -f *.o eternity

.PHONY: all clean
	