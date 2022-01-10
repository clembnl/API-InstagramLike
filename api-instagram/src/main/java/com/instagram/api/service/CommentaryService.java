package com.instagram.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.api.dto.commentary.CommentaryDto;
import com.instagram.api.model.Commentary;
import com.instagram.api.model.Post;
import com.instagram.api.model.User;
import com.instagram.api.repository.CommentaryRepository;

@Service
public class CommentaryService {
	
	@Autowired
	private CommentaryRepository commentaryRepository;
	
	public List<CommentaryDto> listCommentarysByPost(Post post) {
		List<Commentary> commentaryList = commentaryRepository.findAllByPost(post);
        List<CommentaryDto> commentarys = new ArrayList<>();
        for (Commentary commentary:commentaryList){
            CommentaryDto commentaryDto = getDtoFromCommentary(commentary);
            commentarys.add(commentaryDto);
        }
        return commentarys;
	}
	
	public void deleteCommentary(final Integer id) {
		commentaryRepository.deleteById(id);
	}
	
    public void addCommentary(CommentaryDto commentaryDto) {
        Commentary commentary = getCommentaryFromDto(commentaryDto);
        commentaryRepository.save(commentary);
    }
    
    public void updateCommentary(CommentaryDto comDto, User user, Post post){
        Commentary com = commentaryRepository.findById(comDto.getId()).get();
        com.setCommentary(comDto.getCommentary());
        commentaryRepository.save(com);
    }
	
    public static CommentaryDto getDtoFromCommentary(Commentary commentary) {
        CommentaryDto commentaryDto = new CommentaryDto(commentary);
        return commentaryDto;
    }
    
    public static Commentary getCommentaryFromDto(CommentaryDto commentaryDto) {
        Commentary commentary = new Commentary(commentaryDto);
        return commentary;
    }

}
