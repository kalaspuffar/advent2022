use std::fs::File;
use std::io::{ self, BufRead, BufReader };

fn read_lines(filename: String) -> io::Lines<BufReader<File>> {
    let file = File::open(filename).unwrap();
    return io::BufReader::new(file).lines();
}

fn main() {
    let lines = read_lines("../../inputs/day3.txt".to_string());

    let alphabet = "0abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    let mut score: i32 = 0;
    let mut lines_count = 0;
    let mut first = String::new();
    let mut second = String::new();
    let mut third = String::new();

    for line in lines {
        if let Ok(line_unwrapped) = line {

            if lines_count == 0 {
                first = line_unwrapped;
                lines_count += 1;
                continue;
            }
            if lines_count == 1 {
                second = line_unwrapped;
                lines_count += 1;
                continue;
            }
            if lines_count == 2 {
                third = line_unwrapped;
            }

            let mut found_same = Vec::new();

            'outer:
            for first_char in first.split("") {
                if first_char.trim().is_empty() {
                    continue;
                }
                for second_char in second.split("") {
                    if second_char.trim().is_empty() {
                        continue;
                    }
                    if first_char.eq(second_char) {
                        found_same.push(first_char);
                    }
                }
                for third_char in third.split("") {
                    if third_char.trim().is_empty() {
                        continue;
                    }
                    if found_same.contains(&third_char) {
                        score += alphabet.find(third_char).unwrap() as i32;
                        break 'outer;
                    }
                }
            }

            lines_count = 0;
            first = String::new();
            second = String::new();
            third = String::new();
        }
    }

    println!("{}", score);
}