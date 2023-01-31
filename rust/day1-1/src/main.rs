use std::fs::File;
use std::io::{ self, BufRead, BufReader };

fn read_lines(filename: String) -> io::Lines<BufReader<File>> {
    let file = File::open(filename).unwrap();
    return io::BufReader::new(file).lines();
}

fn main() {
    let lines = read_lines("../../inputs/day1.txt".to_string());

    let mut largest: i32 = 0;
    let mut current: i32 = 0;
    for line in lines {
        if let Ok(line_unwrapped) = line {
            if line_unwrapped.to_string().trim().is_empty() {
                current = 0;
                continue;
            }

            current += line_unwrapped.parse::<i32>().unwrap();

            if current > largest {
                largest = current;
            }
        }
    }
    println!("{}", largest);
}