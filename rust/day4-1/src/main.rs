use std::fs::File;
use std::io::{ self, BufRead, BufReader };

fn read_lines(filename: String) -> io::Lines<BufReader<File>> {
    let file = File::open(filename).unwrap();
    return io::BufReader::new(file).lines();
}

fn main() {
//    let lines = read_lines("./testdata/day4.txt".to_string());
    let lines = read_lines("../../inputs/day4.txt".to_string());

    let mut num_pairs: i32 = 0;
    let mut all: i32 = 0;
    let mut no_overlap_count: i32 = 0;

    for line in lines {
        if let Ok(line_unwrapped) = line {
            let ranges: Vec<&str> = line_unwrapped.split(",").collect();
            let first_range: Vec<&str> = ranges[0].split("-").collect();
            let second_range: Vec<&str> = ranges[1].split("-").collect();
            let first_start = first_range[0].parse::<i32>().unwrap();
            let first_end = first_range[1].parse::<i32>().unwrap();
            let second_start = second_range[0].parse::<i32>().unwrap();
            let second_end = second_range[1].parse::<i32>().unwrap();


            if first_start <= second_start && first_end >= second_end {
                num_pairs += 1;
            } else if first_start >= second_start && first_end <= second_end {
                num_pairs += 1;
            }

            if first_start > second_end {
                no_overlap_count += 1;
            } else if second_start > first_end {
                no_overlap_count += 1;
            }

            all += 1;
        }
    }

    println!("Num of overlapp {}", num_pairs);
    println!("Num of overlapping {}", no_overlap_count);
    println!("Num of non-overlapping {}", all - no_overlap_count);
}