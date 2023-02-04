package com.watchapedia.watchpedia_user.service.content;

import com.watchapedia.watchpedia_user.model.dto.content.BookDto;
import com.watchapedia.watchpedia_user.model.entity.content.Book;
import com.watchapedia.watchpedia_user.model.entity.content.ajax.Star;
import com.watchapedia.watchpedia_user.model.network.response.content.BookResponse;

import com.watchapedia.watchpedia_user.model.dto.comment.CommentDto;
import com.watchapedia.watchpedia_user.model.entity.User;
import com.watchapedia.watchpedia_user.model.entity.comment.Comment;
import com.watchapedia.watchpedia_user.model.entity.comment.Spoiler;
import com.watchapedia.watchpedia_user.model.network.response.comment.CommentResponse;

import com.watchapedia.watchpedia_user.model.repository.comment.CommentRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.LikeRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.RecommentRepository;
import com.watchapedia.watchpedia_user.model.repository.comment.SpoilerRepository;
import com.watchapedia.watchpedia_user.model.repository.content.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 책 리스트 뽑아오는 api 로직 서비스 단입니다.


@Service
@RequiredArgsConstructor
public class BookService {

     final BookRepository bookRepository;
    @Transactional(readOnly = true)
    public BookResponse bookView(Long bookIdx) {
        BookDto boo = bookRepository.findById(bookIdx).map(BookDto::from).get();
        return BookResponse.from(boo);
    }


    final CommentRepository commentRepository;
    final SpoilerRepository spoilerRepository;
    final LikeRepository likeRepository;
    final RecommentRepository recommentRepository;
    @Transactional(readOnly = true)
    public List<CommentResponse> commentList(Long bookIdx, User user){
        List<CommentResponse> commentResponseList =
                commentRepository.findByCommContentTypeAndCommContentIdxOrderByCommIdxDesc("book",bookIdx)
                        .stream().map(CommentDto::from).map(
                                comm -> {
                                    Comment com = commentRepository.getReferenceById(comm.idx());
                                    Spoiler spo = spoilerRepository.findBySpoCommentIdx(com);
                                    if(spo != null){
                                        return CommentResponse.from(comm,true,
                                                likeRepository.findByLikeCommentIdx(com),
                                                recommentRepository.findByRecommCommIdx(com),
                                                likeRepository.findByLikeCommentIdxAndLikeUserIdx(com,user) != null ?
                                                        true : false
                                        );
                                    }else{
                                        return CommentResponse.from(comm,false,
                                                likeRepository.findByLikeCommentIdx(com),
                                                recommentRepository.findByRecommCommIdx(com),
                                                likeRepository.findByLikeCommentIdxAndLikeUserIdx(com,user) != null ?
                                                        true : false
                                        );
                                    }
                                }
                        ).toList();
        return commentResponseList;
    }

    @Transactional(readOnly = true)
    public List<Book> searchBooks(){
        return bookRepository.findAll();
    }
}

