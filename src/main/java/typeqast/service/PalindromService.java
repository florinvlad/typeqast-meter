package typeqast.service;

import org.springframework.stereotype.Service;
import typeqast.util.PalindromChecker;

@Service
public class PalindromService {

    public boolean checkPalindrome(String input) {
        return PalindromChecker.check(input);
    }

}
