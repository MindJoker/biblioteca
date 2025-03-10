package org.dirimo.biblioteca.resources.block;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockRepository blockRepository;

    @PostConstruct
    private void initializeBlockchain(){
        if(blockRepository.count() == 0){
            Block genesisBlock = new Block("Genesis Block", null);
            blockRepository.save(genesisBlock);
        }
    }

    public Block create(String data) {
        Block previousBlock = blockRepository.findTopByOrderByIdDesc();
        Block newBlock = new Block(data, previousBlock.getHash());
        return blockRepository.save(newBlock);
    }

    public List<Block> list(){
        return blockRepository.findAll();
    }

    public Optional<Block> getById(Long id){
        return blockRepository.findById(id);
    }

    public boolean isChainValid(){
        List<Block> chain = blockRepository.findAll();
        for(int i = 1; i < chain.size(); i++){
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);


            if(!currentBlock.getHash().equals(currentBlock.calcHash())){
                return false;
            }

            if(!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }

    public void resetBlockchain(){
        blockRepository.truncateTable();
        initializeBlockchain();
    }
}
