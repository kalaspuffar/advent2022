use std::fs::File;
use std::io::{ self, BufRead, BufReader };
use substring::Substring;

fn read_lines(filename: String) -> io::Lines<BufReader<File>> {
    let file = File::open(filename).unwrap();
    return io::BufReader::new(file).lines();
}

fn main() {
    let lines = read_lines("../../inputs/day3.txt".to_string());

    let alphabet = "0abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    let mut score: i32 = 0;
    for line in lines {
        if let Ok(line_unwrapped) = line {

            let len = line_unwrapped.len();
            let first = line_unwrapped.substring(0, len / 2);
            let second = line_unwrapped.substring(len / 2, len);

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
                        score += alphabet.find(first_char).unwrap() as i32;
                        break 'outer;
                    }
                }
            }
        }
    }

    println!("{}", score);
}