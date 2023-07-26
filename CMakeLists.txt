cmake_minimum_required(VERSION 3.25)
project(jlox C)

add_definitions(${GCC_COVERAGE_COMPILE_FLAGS})

add_compile_options(-Wall -Wextra -Wdouble-promotion)
add_compile_options(-g3)
add_compile_options(-fsanitize=address,undefined -fsanitize-trap=undefined)
add_link_options(-fsanitize=address,undefined -fsanitize-trap=undefined)

set(ASAN_OPTIONS=abort_on_error=1:halt_on_error=1)
set(UBSAN_OPTIONS=abort_on_error=1:halt_on_error=1)
set(CMAKE_C_STANDARD 11)

add_executable(jlox main.c common.h chunk.h chunk.c memory.h memory.c debug.h debug.c value.h value.c)
