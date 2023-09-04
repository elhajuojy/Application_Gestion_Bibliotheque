package ma.yc.sas.dao.impl;

import ma.yc.sas.dao.CrudDao;
import ma.yc.sas.model.Member;

import java.util.List;
import java.util.Optional;

public class MemberDoa implements CrudDao<Member> {
    @Override
    public Optional<Member> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Member> getAll() {
        return null;
    }

    @Override
    public Member save(Member member) {
        return null;
    }

    @Override
    public Member update(Member member, String[] params) {
        return null;
    }

    @Override
    public Member delete(Member member) {
        return null;
    }
}
